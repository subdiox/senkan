package org.apache.commons.io;

import com.kayac.lobi.sdk.migration.datastore.NakamapDatastore.Key;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.output.NullOutputStream;

public class FileUtils {
    public static final File[] EMPTY_FILE_ARRAY = new File[0];
    private static final long FILE_COPY_BUFFER_SIZE = 31457280;
    public static final long ONE_EB = 1152921504606846976L;
    public static final long ONE_GB = 1073741824;
    public static final long ONE_KB = 1024;
    public static final long ONE_MB = 1048576;
    public static final long ONE_PB = 1125899906842624L;
    public static final long ONE_TB = 1099511627776L;
    public static final BigInteger ONE_YB = ONE_ZB.multiply(BigInteger.valueOf(ONE_EB));
    public static final BigInteger ONE_ZB = BigInteger.valueOf(1024).multiply(BigInteger.valueOf(ONE_EB));
    private static final Charset UTF8 = Charset.forName("UTF-8");

    public static File getFile(File directory, String... names) {
        if (directory == null) {
            throw new NullPointerException("directorydirectory must not be null");
        } else if (names == null) {
            throw new NullPointerException("names must not be null");
        } else {
            File file = directory;
            String[] arr$ = names;
            int len$ = arr$.length;
            int i$ = 0;
            File file2 = file;
            while (i$ < len$) {
                i$++;
                file2 = new File(file2, arr$[i$]);
            }
            return file2;
        }
    }

    public static File getFile(String... names) {
        if (names == null) {
            throw new NullPointerException("names must not be null");
        }
        String[] arr$ = names;
        int len$ = arr$.length;
        int i$ = 0;
        File file = null;
        while (i$ < len$) {
            File file2;
            String name = arr$[i$];
            if (file == null) {
                file2 = new File(name);
            } else {
                file2 = new File(file, name);
            }
            i$++;
            file = file2;
        }
        return file;
    }

    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }

    public static File getTempDirectory() {
        return new File(getTempDirectoryPath());
    }

    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }

    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }

    public static FileInputStream openInputStream(File file) throws IOException {
        if (!file.exists()) {
            throw new FileNotFoundException("File '" + file + "' does not exist");
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (file.canRead()) {
            return new FileInputStream(file);
        } else {
            throw new IOException("File '" + file + "' cannot be read");
        }
    }

    public static FileOutputStream openOutputStream(File file) throws IOException {
        return openOutputStream(file, false);
    }

    public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
        if (!file.exists()) {
            File parent = file.getParentFile();
            if (!(parent == null || parent.mkdirs() || parent.isDirectory())) {
                throw new IOException("Directory '" + parent + "' could not be created");
            }
        } else if (file.isDirectory()) {
            throw new IOException("File '" + file + "' exists but is a directory");
        } else if (!file.canWrite()) {
            throw new IOException("File '" + file + "' cannot be written to");
        }
        return new FileOutputStream(file, append);
    }

    public static String byteCountToDisplaySize(long size) {
        if (size / ONE_EB > 0) {
            return String.valueOf(size / ONE_EB) + " EB";
        }
        if (size / ONE_PB > 0) {
            return String.valueOf(size / ONE_PB) + " PB";
        }
        if (size / ONE_TB > 0) {
            return String.valueOf(size / ONE_TB) + " TB";
        }
        if (size / ONE_GB > 0) {
            return String.valueOf(size / ONE_GB) + " GB";
        }
        if (size / ONE_MB > 0) {
            return String.valueOf(size / ONE_MB) + " MB";
        }
        if (size / 1024 > 0) {
            return String.valueOf(size / 1024) + " KB";
        }
        return String.valueOf(size) + " bytes";
    }

    public static void touch(File file) throws IOException {
        if (!file.exists()) {
            IOUtils.closeQuietly(openOutputStream(file));
        }
        if (!file.setLastModified(System.currentTimeMillis())) {
            throw new IOException("Unable to set the last modification time for " + file);
        }
    }

    public static File[] convertFileCollectionToFileArray(Collection<File> files) {
        return (File[]) files.toArray(new File[files.size()]);
    }

    private static void innerListFiles(Collection<File> files, File directory, IOFileFilter filter, boolean includeSubDirectories) {
        File[] found = directory.listFiles(filter);
        if (found != null) {
            for (File file : found) {
                if (file.isDirectory()) {
                    if (includeSubDirectories) {
                        files.add(file);
                    }
                    innerListFiles(files, file, filter, includeSubDirectories);
                } else {
                    files.add(file);
                }
            }
        }
    }

    public static Collection<File> listFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        validateListFilesParameters(directory, fileFilter);
        IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
        IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);
        Collection<File> files = new LinkedList();
        innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), false);
        return files;
    }

    private static void validateListFilesParameters(File directory, IOFileFilter fileFilter) {
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Parameter 'directory' is not a directory");
        } else if (fileFilter == null) {
            throw new NullPointerException("Parameter 'fileFilter' is null");
        }
    }

    private static IOFileFilter setUpEffectiveFileFilter(IOFileFilter fileFilter) {
        return FileFilterUtils.and(fileFilter, FileFilterUtils.notFileFilter(DirectoryFileFilter.INSTANCE));
    }

    private static IOFileFilter setUpEffectiveDirFilter(IOFileFilter dirFilter) {
        if (dirFilter == null) {
            return FalseFileFilter.INSTANCE;
        }
        return FileFilterUtils.and(dirFilter, DirectoryFileFilter.INSTANCE);
    }

    public static Collection<File> listFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        validateListFilesParameters(directory, fileFilter);
        IOFileFilter effFileFilter = setUpEffectiveFileFilter(fileFilter);
        IOFileFilter effDirFilter = setUpEffectiveDirFilter(dirFilter);
        Collection<File> files = new LinkedList();
        if (directory.isDirectory()) {
            files.add(directory);
        }
        innerListFiles(files, directory, FileFilterUtils.or(effFileFilter, effDirFilter), true);
        return files;
    }

    public static Iterator<File> iterateFiles(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return listFiles(directory, fileFilter, dirFilter).iterator();
    }

    public static Iterator<File> iterateFilesAndDirs(File directory, IOFileFilter fileFilter, IOFileFilter dirFilter) {
        return listFilesAndDirs(directory, fileFilter, dirFilter).iterator();
    }

    private static String[] toSuffixes(String[] extensions) {
        String[] suffixes = new String[extensions.length];
        for (int i = 0; i < extensions.length; i++) {
            suffixes[i] = "." + extensions[i];
        }
        return suffixes;
    }

    public static Collection<File> listFiles(File directory, String[] extensions, boolean recursive) {
        IOFileFilter filter;
        if (extensions == null) {
            filter = TrueFileFilter.INSTANCE;
        } else {
            filter = new SuffixFileFilter(toSuffixes(extensions));
        }
        return listFiles(directory, filter, recursive ? TrueFileFilter.INSTANCE : FalseFileFilter.INSTANCE);
    }

    public static Iterator<File> iterateFiles(File directory, String[] extensions, boolean recursive) {
        return listFiles(directory, extensions, recursive).iterator();
    }

    public static boolean contentEquals(File file1, File file2) throws IOException {
        Throwable th;
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }
        if (!file1Exists) {
            return true;
        }
        if (file1.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        } else if (file1.length() != file2.length()) {
            return false;
        } else {
            if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
                return true;
            }
            InputStream input1 = null;
            InputStream input2 = null;
            try {
                InputStream input12 = new FileInputStream(file1);
                try {
                    InputStream input22 = new FileInputStream(file2);
                    try {
                        boolean contentEquals = IOUtils.contentEquals(input12, input22);
                        IOUtils.closeQuietly(input12);
                        IOUtils.closeQuietly(input22);
                        return contentEquals;
                    } catch (Throwable th2) {
                        th = th2;
                        input2 = input22;
                        input1 = input12;
                        IOUtils.closeQuietly(input1);
                        IOUtils.closeQuietly(input2);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    input1 = input12;
                    IOUtils.closeQuietly(input1);
                    IOUtils.closeQuietly(input2);
                    throw th;
                }
            } catch (Throwable th4) {
                th = th4;
                IOUtils.closeQuietly(input1);
                IOUtils.closeQuietly(input2);
                throw th;
            }
        }
    }

    public static boolean contentEqualsIgnoreEOL(File file1, File file2, String charsetName) throws IOException {
        Throwable th;
        boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }
        if (!file1Exists) {
            return true;
        }
        if (file1.isDirectory() || file2.isDirectory()) {
            throw new IOException("Can't compare directories, only files");
        } else if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
            return true;
        } else {
            Reader reader = null;
            Reader input2 = null;
            Reader input1;
            if (charsetName == null) {
                try {
                    input1 = new InputStreamReader(new FileInputStream(file1));
                    try {
                        input2 = new InputStreamReader(new FileInputStream(file2));
                        reader = input1;
                    } catch (Throwable th2) {
                        th = th2;
                        reader = input1;
                        IOUtils.closeQuietly(reader);
                        IOUtils.closeQuietly(input2);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    IOUtils.closeQuietly(reader);
                    IOUtils.closeQuietly(input2);
                    throw th;
                }
            }
            input1 = new InputStreamReader(new FileInputStream(file1), charsetName);
            input2 = new InputStreamReader(new FileInputStream(file2), charsetName);
            reader = input1;
            boolean contentEqualsIgnoreEOL = IOUtils.contentEqualsIgnoreEOL(reader, input2);
            IOUtils.closeQuietly(reader);
            IOUtils.closeQuietly(input2);
            return contentEqualsIgnoreEOL;
        }
    }

    public static File toFile(URL url) {
        if (url == null || !Key.FILE.equalsIgnoreCase(url.getProtocol())) {
            return null;
        }
        return new File(decodeUrl(url.getFile().replace(IOUtils.DIR_SEPARATOR_UNIX, File.separatorChar)));
    }

    static String decodeUrl(String url) {
        String decoded = url;
        if (url == null || url.indexOf(37) < 0) {
            return decoded;
        }
        int n = url.length();
        StringBuffer buffer = new StringBuffer();
        ByteBuffer bytes = ByteBuffer.allocate(n);
        int i = 0;
        while (i < n) {
            if (url.charAt(i) == '%') {
                do {
                    try {
                        bytes.put((byte) Integer.parseInt(url.substring(i + 1, i + 3), 16));
                        i += 3;
                        if (i >= n) {
                            break;
                        }
                    } catch (RuntimeException e) {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(UTF8.decode(bytes).toString());
                            bytes.clear();
                        }
                    } catch (Throwable th) {
                        if (bytes.position() > 0) {
                            bytes.flip();
                            buffer.append(UTF8.decode(bytes).toString());
                            bytes.clear();
                        }
                    }
                } while (url.charAt(i) == '%');
                if (bytes.position() > 0) {
                    bytes.flip();
                    buffer.append(UTF8.decode(bytes).toString());
                    bytes.clear();
                }
            }
            int i2 = i + 1;
            buffer.append(url.charAt(i));
            i = i2;
        }
        return buffer.toString();
    }

    public static File[] toFiles(URL[] urls) {
        if (urls == null || urls.length == 0) {
            return EMPTY_FILE_ARRAY;
        }
        File[] files = new File[urls.length];
        for (int i = 0; i < urls.length; i++) {
            URL url = urls[i];
            if (url != null) {
                if (url.getProtocol().equals(Key.FILE)) {
                    files[i] = toFile(url);
                } else {
                    throw new IllegalArgumentException("URL could not be converted to a File: " + url);
                }
            }
        }
        return files;
    }

    public static URL[] toURLs(File[] files) throws IOException {
        URL[] urls = new URL[files.length];
        for (int i = 0; i < urls.length; i++) {
            urls[i] = files[i].toURI().toURL();
        }
        return urls;
    }

    public static void copyFileToDirectory(File srcFile, File destDir) throws IOException {
        copyFileToDirectory(srcFile, destDir, true);
    }

    public static void copyFileToDirectory(File srcFile, File destDir, boolean preserveFileDate) throws IOException {
        if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!destDir.exists() || destDir.isDirectory()) {
            copyFile(srcFile, new File(destDir, srcFile.getName()), preserveFileDate);
        } else {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
    }

    public static void copyFile(File srcFile, File destFile) throws IOException {
        copyFile(srcFile, destFile, true);
    }

    public static void copyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        } else if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' exists but is a directory");
        } else if (srcFile.getCanonicalPath().equals(destFile.getCanonicalPath())) {
            throw new IOException("Source '" + srcFile + "' and destination '" + destFile + "' are the same");
        } else {
            File parentFile = destFile.getParentFile();
            if (parentFile != null && !parentFile.mkdirs() && !parentFile.isDirectory()) {
                throw new IOException("Destination '" + parentFile + "' directory cannot be created");
            } else if (!destFile.exists() || destFile.canWrite()) {
                doCopyFile(srcFile, destFile, preserveFileDate);
            } else {
                throw new IOException("Destination '" + destFile + "' exists but is read-only");
            }
        }
    }

    public static long copyFile(File input, OutputStream output) throws IOException {
        InputStream fis = new FileInputStream(input);
        try {
            long copyLarge = IOUtils.copyLarge(fis, output);
            return copyLarge;
        } finally {
            fis.close();
        }
    }

    private static void doCopyFile(File srcFile, File destFile, boolean preserveFileDate) throws IOException {
        Throwable th;
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }
        InputStream fis = null;
        OutputStream fos = null;
        try {
            InputStream fis2 = new FileInputStream(srcFile);
            try {
                OutputStream fos2 = new FileOutputStream(destFile);
                try {
                    Closeable input = fis2.getChannel();
                    Closeable output = fos2.getChannel();
                    long size = input.size();
                    long pos = 0;
                    while (pos < size) {
                        pos += output.transferFrom(input, pos, size - pos > FILE_COPY_BUFFER_SIZE ? FILE_COPY_BUFFER_SIZE : size - pos);
                    }
                    IOUtils.closeQuietly(output);
                    IOUtils.closeQuietly(fos2);
                    IOUtils.closeQuietly(input);
                    IOUtils.closeQuietly(fis2);
                    if (srcFile.length() != destFile.length()) {
                        throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
                    } else if (preserveFileDate) {
                        destFile.setLastModified(srcFile.lastModified());
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fos = fos2;
                    fis = fis2;
                    IOUtils.closeQuietly((Closeable) null);
                    IOUtils.closeQuietly(fos);
                    IOUtils.closeQuietly((Closeable) null);
                    IOUtils.closeQuietly(fis);
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                fis = fis2;
                IOUtils.closeQuietly((Closeable) null);
                IOUtils.closeQuietly(fos);
                IOUtils.closeQuietly((Closeable) null);
                IOUtils.closeQuietly(fis);
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            IOUtils.closeQuietly((Closeable) null);
            IOUtils.closeQuietly(fos);
            IOUtils.closeQuietly((Closeable) null);
            IOUtils.closeQuietly(fis);
            throw th;
        }
    }

    public static void copyDirectoryToDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        } else if (srcDir.exists() && !srcDir.isDirectory()) {
            throw new IllegalArgumentException("Source '" + destDir + "' is not a directory");
        } else if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!destDir.exists() || destDir.isDirectory()) {
            copyDirectory(srcDir, new File(destDir, srcDir.getName()), true);
        } else {
            throw new IllegalArgumentException("Destination '" + destDir + "' is not a directory");
        }
    }

    public static void copyDirectory(File srcDir, File destDir) throws IOException {
        copyDirectory(srcDir, destDir, true);
    }

    public static void copyDirectory(File srcDir, File destDir, boolean preserveFileDate) throws IOException {
        copyDirectory(srcDir, destDir, null, preserveFileDate);
    }

    public static void copyDirectory(File srcDir, File destDir, FileFilter filter) throws IOException {
        copyDirectory(srcDir, destDir, filter, true);
    }

    public static void copyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        } else if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' exists but is not a directory");
        } else if (srcDir.getCanonicalPath().equals(destDir.getCanonicalPath())) {
            throw new IOException("Source '" + srcDir + "' and destination '" + destDir + "' are the same");
        } else {
            List<String> exclusionList = null;
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
                File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
                if (srcFiles != null && srcFiles.length > 0) {
                    exclusionList = new ArrayList(srcFiles.length);
                    for (File srcFile : srcFiles) {
                        exclusionList.add(new File(destDir, srcFile.getName()).getCanonicalPath());
                    }
                }
            }
            doCopyDirectory(srcDir, destDir, filter, preserveFileDate, exclusionList);
        }
    }

    private static void doCopyDirectory(File srcDir, File destDir, FileFilter filter, boolean preserveFileDate, List<String> exclusionList) throws IOException {
        File[] srcFiles = filter == null ? srcDir.listFiles() : srcDir.listFiles(filter);
        if (srcFiles == null) {
            throw new IOException("Failed to list contents of " + srcDir);
        }
        if (destDir.exists()) {
            if (!destDir.isDirectory()) {
                throw new IOException("Destination '" + destDir + "' exists but is not a directory");
            }
        } else if (!(destDir.mkdirs() || destDir.isDirectory())) {
            throw new IOException("Destination '" + destDir + "' directory cannot be created");
        }
        if (destDir.canWrite()) {
            for (File srcFile : srcFiles) {
                File dstFile = new File(destDir, srcFile.getName());
                if (exclusionList == null || !exclusionList.contains(srcFile.getCanonicalPath())) {
                    if (srcFile.isDirectory()) {
                        doCopyDirectory(srcFile, dstFile, filter, preserveFileDate, exclusionList);
                    } else {
                        doCopyFile(srcFile, dstFile, preserveFileDate);
                    }
                }
            }
            if (preserveFileDate) {
                destDir.setLastModified(srcDir.lastModified());
                return;
            }
            return;
        }
        throw new IOException("Destination '" + destDir + "' cannot be written to");
    }

    public static void copyURLToFile(URL source, File destination) throws IOException {
        copyInputStreamToFile(source.openStream(), destination);
    }

    public static void copyURLToFile(URL source, File destination, int connectionTimeout, int readTimeout) throws IOException {
        URLConnection connection = source.openConnection();
        connection.setConnectTimeout(connectionTimeout);
        connection.setReadTimeout(readTimeout);
        copyInputStreamToFile(connection.getInputStream(), destination);
    }

    public static void copyInputStreamToFile(InputStream source, File destination) throws IOException {
        OutputStream output;
        try {
            output = openOutputStream(destination);
            IOUtils.copy(source, output);
            output.close();
            IOUtils.closeQuietly(output);
            IOUtils.closeQuietly(source);
        } catch (Throwable th) {
            IOUtils.closeQuietly(source);
        }
    }

    public static void deleteDirectory(File directory) throws IOException {
        if (directory.exists()) {
            if (!isSymlink(directory)) {
                cleanDirectory(directory);
            }
            if (!directory.delete()) {
                throw new IOException("Unable to delete directory " + directory + ".");
            }
        }
    }

    public static boolean deleteQuietly(File file) {
        boolean z = false;
        if (file != null) {
            try {
                if (file.isDirectory()) {
                    cleanDirectory(file);
                }
            } catch (Exception e) {
            }
            try {
                z = file.delete();
            } catch (Exception e2) {
            }
        }
        return z;
    }

    public static boolean directoryContains(File directory, File child) throws IOException {
        if (directory == null) {
            throw new IllegalArgumentException("Directory must not be null");
        } else if (!directory.isDirectory()) {
            throw new IllegalArgumentException("Not a directory: " + directory);
        } else if (child != null && directory.exists() && child.exists()) {
            return FilenameUtils.directoryContains(directory.getCanonicalPath(), child.getCanonicalPath());
        } else {
            return false;
        }
    }

    public static void cleanDirectory(File directory) throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        } else if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IOException("Failed to list contents of " + directory);
            }
            IOException exception = null;
            for (File file : files) {
                try {
                    forceDelete(file);
                } catch (IOException ioe) {
                    exception = ioe;
                }
            }
            if (exception != null) {
                throw exception;
            }
        } else {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    public static boolean waitFor(File file, int seconds) {
        int timeout = 0;
        int tick = 0;
        while (!file.exists()) {
            int tick2 = tick + 1;
            if (tick >= 10) {
                tick = 0;
                int timeout2 = timeout + 1;
                if (timeout > seconds) {
                    timeout = timeout2;
                    return false;
                }
                timeout = timeout2;
            } else {
                tick = tick2;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            } catch (Exception e2) {
            }
        }
        return true;
    }

    public static String readFileToString(File file, String encoding) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = openInputStream(file);
            String iOUtils = IOUtils.toString(inputStream, encoding);
            return iOUtils;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static String readFileToString(File file) throws IOException {
        return readFileToString(file, null);
    }

    public static byte[] readFileToByteArray(File file) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = openInputStream(file);
            byte[] toByteArray = IOUtils.toByteArray(inputStream, file.length());
            return toByteArray;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static List<String> readLines(File file, String encoding) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = openInputStream(file);
            List<String> readLines = IOUtils.readLines(inputStream, encoding);
            return readLines;
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    public static List<String> readLines(File file) throws IOException {
        return readLines(file, null);
    }

    public static LineIterator lineIterator(File file, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = openInputStream(file);
            return IOUtils.lineIterator(in, encoding);
        } catch (IOException ex) {
            IOUtils.closeQuietly(in);
            throw ex;
        } catch (RuntimeException ex2) {
            IOUtils.closeQuietly(in);
            throw ex2;
        }
    }

    public static LineIterator lineIterator(File file) throws IOException {
        return lineIterator(file, null);
    }

    public static void writeStringToFile(File file, String data, String encoding) throws IOException {
        writeStringToFile(file, data, encoding, false);
    }

    public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = openOutputStream(file, append);
            IOUtils.write(data, outputStream, encoding);
            outputStream.close();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static void writeStringToFile(File file, String data) throws IOException {
        writeStringToFile(file, data, null, false);
    }

    public static void writeStringToFile(File file, String data, boolean append) throws IOException {
        writeStringToFile(file, data, null, append);
    }

    public static void write(File file, CharSequence data) throws IOException {
        write(file, data, null, false);
    }

    public static void write(File file, CharSequence data, boolean append) throws IOException {
        write(file, data, null, append);
    }

    public static void write(File file, CharSequence data, String encoding) throws IOException {
        write(file, data, encoding, false);
    }

    public static void write(File file, CharSequence data, String encoding, boolean append) throws IOException {
        writeStringToFile(file, data == null ? null : data.toString(), encoding, append);
    }

    public static void writeByteArrayToFile(File file, byte[] data) throws IOException {
        writeByteArrayToFile(file, data, false);
    }

    public static void writeByteArrayToFile(File file, byte[] data, boolean append) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = openOutputStream(file, append);
            outputStream.write(data);
            outputStream.close();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static void writeLines(File file, String encoding, Collection<?> lines) throws IOException {
        writeLines(file, encoding, lines, null, false);
    }

    public static void writeLines(File file, String encoding, Collection<?> lines, boolean append) throws IOException {
        writeLines(file, encoding, lines, null, append);
    }

    public static void writeLines(File file, Collection<?> lines) throws IOException {
        writeLines(file, null, lines, null, false);
    }

    public static void writeLines(File file, Collection<?> lines, boolean append) throws IOException {
        writeLines(file, null, lines, null, append);
    }

    public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding) throws IOException {
        writeLines(file, encoding, lines, lineEnding, false);
    }

    public static void writeLines(File file, String encoding, Collection<?> lines, String lineEnding, boolean append) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = openOutputStream(file, append);
            IOUtils.writeLines(lines, lineEnding, outputStream, encoding);
            outputStream.close();
        } finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding) throws IOException {
        writeLines(file, null, lines, lineEnding, false);
    }

    public static void writeLines(File file, Collection<?> lines, String lineEnding, boolean append) throws IOException {
        writeLines(file, null, lines, lineEnding, append);
    }

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectory(file);
            return;
        }
        boolean filePresent = file.exists();
        if (!file.delete()) {
            if (filePresent) {
                throw new IOException("Unable to delete file: " + file);
            }
            throw new FileNotFoundException("File does not exist: " + file);
        }
    }

    public static void forceDeleteOnExit(File file) throws IOException {
        if (file.isDirectory()) {
            deleteDirectoryOnExit(file);
        } else {
            file.deleteOnExit();
        }
    }

    private static void deleteDirectoryOnExit(File directory) throws IOException {
        if (directory.exists()) {
            directory.deleteOnExit();
            if (!isSymlink(directory)) {
                cleanDirectoryOnExit(directory);
            }
        }
    }

    private static void cleanDirectoryOnExit(File directory) throws IOException {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        } else if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files == null) {
                throw new IOException("Failed to list contents of " + directory);
            }
            IOException exception = null;
            for (File file : files) {
                try {
                    forceDeleteOnExit(file);
                } catch (IOException ioe) {
                    exception = ioe;
                }
            }
            if (exception != null) {
                throw exception;
            }
        } else {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    public static void forceMkdir(File directory) throws IOException {
        if (directory.exists()) {
            if (!directory.isDirectory()) {
                throw new IOException("File " + directory + " exists and is " + "not a directory. Unable to create directory.");
            }
        } else if (!directory.mkdirs() && !directory.isDirectory()) {
            throw new IOException("Unable to create directory " + directory);
        }
    }

    public static long sizeOf(File file) {
        if (!file.exists()) {
            throw new IllegalArgumentException(file + " does not exist");
        } else if (file.isDirectory()) {
            return sizeOfDirectory(file);
        } else {
            return file.length();
        }
    }

    public static long sizeOfDirectory(File directory) {
        if (!directory.exists()) {
            throw new IllegalArgumentException(directory + " does not exist");
        } else if (directory.isDirectory()) {
            long size = 0;
            File[] files = directory.listFiles();
            if (files == null) {
                return 0;
            }
            for (File file : files) {
                size += sizeOf(file);
            }
            return size;
        } else {
            throw new IllegalArgumentException(directory + " is not a directory");
        }
    }

    public static boolean isFileNewer(File file, File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        } else if (reference.exists()) {
            return isFileNewer(file, reference.lastModified());
        } else {
            throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
        }
    }

    public static boolean isFileNewer(File file, Date date) {
        if (date != null) {
            return isFileNewer(file, date.getTime());
        }
        throw new IllegalArgumentException("No specified date");
    }

    public static boolean isFileNewer(File file, long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        } else if (file.exists() && file.lastModified() > timeMillis) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isFileOlder(File file, File reference) {
        if (reference == null) {
            throw new IllegalArgumentException("No specified reference file");
        } else if (reference.exists()) {
            return isFileOlder(file, reference.lastModified());
        } else {
            throw new IllegalArgumentException("The reference file '" + reference + "' doesn't exist");
        }
    }

    public static boolean isFileOlder(File file, Date date) {
        if (date != null) {
            return isFileOlder(file, date.getTime());
        }
        throw new IllegalArgumentException("No specified date");
    }

    public static boolean isFileOlder(File file, long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        } else if (file.exists() && file.lastModified() < timeMillis) {
            return true;
        } else {
            return false;
        }
    }

    public static long checksumCRC32(File file) throws IOException {
        CRC32 crc = new CRC32();
        checksum(file, crc);
        return crc.getValue();
    }

    public static Checksum checksum(File file, Checksum checksum) throws IOException {
        Throwable th;
        if (file.isDirectory()) {
            throw new IllegalArgumentException("Checksums can't be computed on directories");
        }
        InputStream in = null;
        try {
            InputStream in2 = new CheckedInputStream(new FileInputStream(file), checksum);
            try {
                IOUtils.copy(in2, new NullOutputStream());
                IOUtils.closeQuietly(in2);
                return checksum;
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                IOUtils.closeQuietly(in);
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            IOUtils.closeQuietly(in);
            throw th;
        }
    }

    public static void moveDirectory(File srcDir, File destDir) throws IOException {
        if (srcDir == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!srcDir.exists()) {
            throw new FileNotFoundException("Source '" + srcDir + "' does not exist");
        } else if (!srcDir.isDirectory()) {
            throw new IOException("Source '" + srcDir + "' is not a directory");
        } else if (destDir.exists()) {
            throw new FileExistsException("Destination '" + destDir + "' already exists");
        } else if (!srcDir.renameTo(destDir)) {
            if (destDir.getCanonicalPath().startsWith(srcDir.getCanonicalPath())) {
                throw new IOException("Cannot move directory: " + srcDir + " to a subdirectory of itself: " + destDir);
            }
            copyDirectory(srcDir, destDir);
            deleteDirectory(srcDir);
            if (srcDir.exists()) {
                throw new IOException("Failed to delete original directory '" + srcDir + "' after copy to '" + destDir + "'");
            }
        }
    }

    public static void moveDirectoryToDirectory(File src, File destDir, boolean createDestDir) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir == null) {
            throw new NullPointerException("Destination directory must not be null");
        } else {
            if (!destDir.exists() && createDestDir) {
                destDir.mkdirs();
            }
            if (!destDir.exists()) {
                throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
            } else if (destDir.isDirectory()) {
                moveDirectory(src, new File(destDir, src.getName()));
            } else {
                throw new IOException("Destination '" + destDir + "' is not a directory");
            }
        }
    }

    public static void moveFile(File srcFile, File destFile) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destFile == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!srcFile.exists()) {
            throw new FileNotFoundException("Source '" + srcFile + "' does not exist");
        } else if (srcFile.isDirectory()) {
            throw new IOException("Source '" + srcFile + "' is a directory");
        } else if (destFile.exists()) {
            throw new FileExistsException("Destination '" + destFile + "' already exists");
        } else if (destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' is a directory");
        } else if (!srcFile.renameTo(destFile)) {
            copyFile(srcFile, destFile);
            if (!srcFile.delete()) {
                deleteQuietly(destFile);
                throw new IOException("Failed to delete original file '" + srcFile + "' after copy to '" + destFile + "'");
            }
        }
    }

    public static void moveFileToDirectory(File srcFile, File destDir, boolean createDestDir) throws IOException {
        if (srcFile == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir == null) {
            throw new NullPointerException("Destination directory must not be null");
        } else {
            if (!destDir.exists() && createDestDir) {
                destDir.mkdirs();
            }
            if (!destDir.exists()) {
                throw new FileNotFoundException("Destination directory '" + destDir + "' does not exist [createDestDir=" + createDestDir + "]");
            } else if (destDir.isDirectory()) {
                moveFile(srcFile, new File(destDir, srcFile.getName()));
            } else {
                throw new IOException("Destination '" + destDir + "' is not a directory");
            }
        }
    }

    public static void moveToDirectory(File src, File destDir, boolean createDestDir) throws IOException {
        if (src == null) {
            throw new NullPointerException("Source must not be null");
        } else if (destDir == null) {
            throw new NullPointerException("Destination must not be null");
        } else if (!src.exists()) {
            throw new FileNotFoundException("Source '" + src + "' does not exist");
        } else if (src.isDirectory()) {
            moveDirectoryToDirectory(src, destDir, createDestDir);
        } else {
            moveFileToDirectory(src, destDir, createDestDir);
        }
    }

    public static boolean isSymlink(File file) throws IOException {
        if (file == null) {
            throw new NullPointerException("File must not be null");
        } else if (FilenameUtils.isSystemWindows()) {
            return false;
        } else {
            File fileInCanonicalDir;
            if (file.getParent() == null) {
                fileInCanonicalDir = file;
            } else {
                fileInCanonicalDir = new File(file.getParentFile().getCanonicalFile(), file.getName());
            }
            if (fileInCanonicalDir.getCanonicalFile().equals(fileInCanonicalDir.getAbsoluteFile())) {
                return false;
            }
            return true;
        }
    }
}
