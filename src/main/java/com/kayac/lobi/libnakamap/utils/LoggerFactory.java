package com.kayac.lobi.libnakamap.utils;

import android.os.Environment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoggerFactory {
    private static final String CLASS_METHOD_SEPARATOR = "::";
    private static final String D = "D ";
    private static final String E = "E ";
    private static final boolean FILE_LOG_ENABLED = false;
    private static final String I = "I ";
    private static final String LINE_INDICATOR = " @L";
    private static final String SPACE = " ";
    private static final String V = "V ";
    private static final String W = "W ";
    private static FileLogger mFileLogger = null;
    public boolean isEnabled = true;

    private static class FileLogger implements Runnable, Logger {
        private static final Date DATE = new Date();
        private static final String NL = "\n";
        private final ExecutorService mExecutorService;
        private final StringBuffer mLogBuffer;
        private File mLogFile;

        private FileLogger() {
            this.mExecutorService = Executors.newSingleThreadExecutor();
            this.mLogBuffer = new StringBuffer(8192);
            this.mLogFile = null;
        }

        FileLogger appendTimeStamp() {
            DATE.setTime(System.currentTimeMillis());
            this.mLogBuffer.append(String.format("%02d:%02d:%02d ", new Object[]{Integer.valueOf(DATE.getHours()), Integer.valueOf(DATE.getMinutes()), Integer.valueOf(DATE.getSeconds())}));
            return this;
        }

        public FileLogger append(CharSequence csq) {
            this.mLogBuffer.append(csq);
            return this;
        }

        public FileLogger newline() {
            this.mLogBuffer.append("\n");
            return this;
        }

        public void run() {
            File file = getLogFile();
            if (file != null) {
                try {
                    FileOutputStream fos = new FileOutputStream(file, true);
                    try {
                        OutputStreamWriter writer = new OutputStreamWriter(fos, Charset.forName("UTF-8").newEncoder());
                        int len = this.mLogBuffer.length();
                        char[] buff = new char[len];
                        this.mLogBuffer.getChars(0, len, buff, 0);
                        try {
                            writer.write(buff);
                            this.mLogBuffer.delete(0, len);
                            if (writer != null) {
                                try {
                                    writer.close();
                                } catch (IOException e) {
                                }
                            }
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e2) {
                                }
                            }
                        } catch (IOException e3) {
                        }
                    } catch (FileNotFoundException e4) {
                        FileOutputStream fileOutputStream = fos;
                    }
                } catch (FileNotFoundException e5) {
                }
            }
        }

        private synchronized File getLogFile() {
            if (this.mLogFile == null) {
                File file = getLogInFile(Environment.getExternalStorageDirectory());
                if (file == null) {
                    file = getLogInFile(Environment.getDownloadCacheDirectory());
                }
                if (file == null) {
                    file = getLogInFile(Environment.getDataDirectory());
                }
                this.mLogFile = file;
            }
            return this.mLogFile;
        }

        private static File getLogInFile(File dir) {
            File file = new File(dir, "nakamap.log");
            if (file.exists()) {
                return file;
            }
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                return null;
            }
        }

        private synchronized void flush() {
            this.mExecutorService.submit(this);
        }

        public void debug(String tag, Object... messages) {
            _log(LoggerFactory.D, tag, buildMessage(messages));
        }

        public void debug(String tag, String message, Throwable t) {
            log(LoggerFactory.D, tag, message, t);
        }

        public void verbose(String tag, Object... messages) {
            _log(LoggerFactory.V, tag, buildMessage(messages));
        }

        public void verbose(String tag, String message, Throwable t) {
            log(LoggerFactory.V, tag, message, t);
        }

        public void info(String tag, Object... messages) {
            _log(LoggerFactory.I, tag, buildMessage(messages));
        }

        public void info(String tag, String message, Throwable t) {
            log(LoggerFactory.I, tag, message, t);
        }

        public void warn(String tag, Object... messages) {
            _log(LoggerFactory.W, tag, buildMessage(messages));
        }

        public void warn(String tag, String message, Throwable t) {
            log(LoggerFactory.W, tag, message, t);
        }

        public void error(String tag, Object... messages) {
            _log(LoggerFactory.E, tag, buildMessage(messages));
        }

        public void error(String tag, String message, Throwable t) {
            log(LoggerFactory.E, tag, message, t);
        }

        void _log(String mode, String tag, String message) {
            appendTimeStamp().append(mode).append(tag).append(LoggerFactory.SPACE).append(message).newline().flush();
        }

        void log(String mode, String tag, String message, Throwable t) {
            _log(mode, tag, message);
            for (StackTraceElement element : t.getStackTrace()) {
                appendTimeStamp().append(LoggerFactory.E).append(tag).append(LoggerFactory.SPACE).append(element.getClassName()).append(LoggerFactory.CLASS_METHOD_SEPARATOR).append(element.getMethodName()).append(LoggerFactory.LINE_INDICATOR).append(element.getLineNumber() + "").newline().flush();
            }
        }

        static final String buildMessage(Object[] messages) {
            StringBuilder builder = new StringBuilder();
            if (messages != null) {
                int len = messages.length;
                for (int i = 0; i < len; i++) {
                    builder.append(messages[i]);
                    if (i < len - 1) {
                        builder.append(LoggerFactory.SPACE);
                    }
                }
            }
            return builder.toString();
        }
    }

    public static synchronized Logger getFileLogger() {
        Logger logger;
        synchronized (LoggerFactory.class) {
            if (mFileLogger == null) {
                mFileLogger = new FileLogger();
            }
            logger = mFileLogger;
        }
        return logger;
    }
}
