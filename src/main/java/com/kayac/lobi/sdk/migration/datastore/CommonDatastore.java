package com.kayac.lobi.sdk.migration.datastore;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

interface CommonDatastore {
    public static final String ASC = " ASC ";
    public static final String DESC = " DESC ";

    public static final class Function {
        public static final void setValueImpl(SQLiteDatabase db, String key, Serializable value) {
            IOException e;
            Throwable th;
            SQLException e2;
            ByteArrayOutputStream os = null;
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                    try {
                        oos2.writeObject(value);
                        ContentValues values = new ContentValues();
                        values.put("c_key", key);
                        values.put("c_value", os2.toByteArray());
                        if (db.replaceOrThrow("key_value_table", null, values) == -1) {
                            if (oos2 != null) {
                                try {
                                    oos2.close();
                                } catch (IOException e3) {
                                }
                            }
                            if (os2 == null) {
                                try {
                                    os2.close();
                                    oos = oos2;
                                    os = os2;
                                } catch (IOException e4) {
                                    oos = oos2;
                                    os = os2;
                                    return;
                                }
                            }
                            os = os2;
                            return;
                        }
                        if (oos2 != null) {
                            oos2.close();
                        }
                        if (os2 == null) {
                            os = os2;
                            return;
                        }
                        os2.close();
                        oos = oos2;
                        os = os2;
                    } catch (IOException e5) {
                        e = e5;
                        oos = oos2;
                        os = os2;
                        try {
                            e.printStackTrace();
                            if (oos != null) {
                                try {
                                    oos.close();
                                } catch (IOException e6) {
                                }
                            }
                            if (os == null) {
                                try {
                                    os.close();
                                } catch (IOException e7) {
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (oos != null) {
                                try {
                                    oos.close();
                                } catch (IOException e8) {
                                }
                            }
                            if (os != null) {
                                try {
                                    os.close();
                                } catch (IOException e9) {
                                }
                            }
                            throw th;
                        }
                    } catch (SQLException e10) {
                        e2 = e10;
                        oos = oos2;
                        os = os2;
                        e2.printStackTrace();
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (IOException e11) {
                            }
                        }
                        if (os == null) {
                            try {
                                os.close();
                            } catch (IOException e12) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        oos = oos2;
                        os = os2;
                        if (oos != null) {
                            oos.close();
                        }
                        if (os != null) {
                            os.close();
                        }
                        throw th;
                    }
                } catch (IOException e13) {
                    e = e13;
                    os = os2;
                    e.printStackTrace();
                    if (oos != null) {
                        oos.close();
                    }
                    if (os == null) {
                        os.close();
                    }
                } catch (SQLException e14) {
                    e2 = e14;
                    os = os2;
                    e2.printStackTrace();
                    if (oos != null) {
                        oos.close();
                    }
                    if (os == null) {
                        os.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    os = os2;
                    if (oos != null) {
                        oos.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                    throw th;
                }
            } catch (IOException e15) {
                e = e15;
                e.printStackTrace();
                if (oos != null) {
                    oos.close();
                }
                if (os == null) {
                    os.close();
                }
            } catch (SQLException e16) {
                e2 = e16;
                e2.printStackTrace();
                if (oos != null) {
                    oos.close();
                }
                if (os == null) {
                    os.close();
                }
            }
        }

        public static final <T> T getValueImpl(SQLiteDatabase db, String key) {
            Cursor c = null;
            T t = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("key_value_table");
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_value"}, "c_key = ?", new String[]{key}, null, null, null);
                if (c.moveToFirst()) {
                    t = new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject();
                }
                if (c != null) {
                    c.close();
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
            return t;
        }

        public static final void deleteValueImpl(SQLiteDatabase db, String key) {
            try {
                db.delete("key_value_table", "c_key = ? ", new String[]{key});
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static final void setKKValueImpl(SQLiteDatabase db, String key1, String key2, Serializable value) {
            IOException e;
            Throwable th;
            SQLException e2;
            ByteArrayOutputStream os = null;
            ObjectOutputStream oos = null;
            try {
                ByteArrayOutputStream os2 = new ByteArrayOutputStream();
                try {
                    ObjectOutputStream oos2 = new ObjectOutputStream(os2);
                    try {
                        oos2.writeObject(value);
                        ContentValues values = new ContentValues();
                        values.put("c_key_1", key1);
                        values.put("c_key_2", key2);
                        values.put("c_value", os2.toByteArray());
                        if (db.replaceOrThrow("key_key_value_table", null, values) == -1) {
                            if (oos2 != null) {
                                try {
                                    oos2.close();
                                } catch (IOException e3) {
                                }
                            }
                            if (os2 == null) {
                                try {
                                    os2.close();
                                    oos = oos2;
                                    os = os2;
                                } catch (IOException e4) {
                                    oos = oos2;
                                    os = os2;
                                    return;
                                }
                            }
                            os = os2;
                            return;
                        }
                        if (oos2 != null) {
                            oos2.close();
                        }
                        if (os2 == null) {
                            os = os2;
                            return;
                        }
                        os2.close();
                        oos = oos2;
                        os = os2;
                    } catch (IOException e5) {
                        e = e5;
                        oos = oos2;
                        os = os2;
                        try {
                            e.printStackTrace();
                            if (oos != null) {
                                try {
                                    oos.close();
                                } catch (IOException e6) {
                                }
                            }
                            if (os == null) {
                                try {
                                    os.close();
                                } catch (IOException e7) {
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (oos != null) {
                                try {
                                    oos.close();
                                } catch (IOException e8) {
                                }
                            }
                            if (os != null) {
                                try {
                                    os.close();
                                } catch (IOException e9) {
                                }
                            }
                            throw th;
                        }
                    } catch (SQLException e10) {
                        e2 = e10;
                        oos = oos2;
                        os = os2;
                        e2.printStackTrace();
                        if (oos != null) {
                            try {
                                oos.close();
                            } catch (IOException e11) {
                            }
                        }
                        if (os == null) {
                            try {
                                os.close();
                            } catch (IOException e12) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        oos = oos2;
                        os = os2;
                        if (oos != null) {
                            oos.close();
                        }
                        if (os != null) {
                            os.close();
                        }
                        throw th;
                    }
                } catch (IOException e13) {
                    e = e13;
                    os = os2;
                    e.printStackTrace();
                    if (oos != null) {
                        oos.close();
                    }
                    if (os == null) {
                        os.close();
                    }
                } catch (SQLException e14) {
                    e2 = e14;
                    os = os2;
                    e2.printStackTrace();
                    if (oos != null) {
                        oos.close();
                    }
                    if (os == null) {
                        os.close();
                    }
                } catch (Throwable th4) {
                    th = th4;
                    os = os2;
                    if (oos != null) {
                        oos.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                    throw th;
                }
            } catch (IOException e15) {
                e = e15;
                e.printStackTrace();
                if (oos != null) {
                    oos.close();
                }
                if (os == null) {
                    os.close();
                }
            } catch (SQLException e16) {
                e2 = e16;
                e2.printStackTrace();
                if (oos != null) {
                    oos.close();
                }
                if (os == null) {
                    os.close();
                }
            }
        }

        public static final <T> T getKKValueImpl(SQLiteDatabase db, String key1, String key2) {
            Cursor c = null;
            T t = null;
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("key_key_value_table");
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_value"}, "c_key_1 = ? AND c_key_2 = ?", new String[]{key1, key2}, null, null, null);
                if (c.moveToFirst()) {
                    t = new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject();
                }
                if (c != null) {
                    c.close();
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
            return t;
        }

        public static final <T> List<T> getKKValuesImpl(SQLiteDatabase db, String key1) {
            Cursor c = null;
            List<T> values = new ArrayList();
            try {
                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                queryBuilder.setTables("key_key_value_table");
                SQLiteDatabase sQLiteDatabase = db;
                c = queryBuilder.query(sQLiteDatabase, new String[]{"c_value"}, "c_key_1 = ? ", new String[]{key1}, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        values.add(new ObjectInputStream(new ByteArrayInputStream(c.getBlob(c.getColumnIndex("c_value")))).readObject());
                    } while (c.moveToNext());
                }
                if (c != null) {
                    c.close();
                }
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (ClassNotFoundException e3) {
                e3.printStackTrace();
                if (c != null) {
                    c.close();
                }
            } catch (Throwable th) {
                if (c != null) {
                    c.close();
                }
            }
            return values;
        }

        public static final void deleteKKValueImpl(SQLiteDatabase db, String key1, String key2) {
            try {
                db.delete("key_key_value_table", "c_key_1 = ? AND c_key_2 = ? ", new String[]{key1, key2});
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
