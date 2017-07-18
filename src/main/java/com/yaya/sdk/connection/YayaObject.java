package com.yaya.sdk.connection;

import java.lang.reflect.Proxy;
import java.util.StringTokenizer;

public class YayaObject {
    protected YayaLib L;
    protected Integer ref;

    protected YayaObject(YayaLib L, String globalName) {
        synchronized (L) {
            this.L = L;
            L.getGlobal(globalName);
            registerValue(-1);
            L.pop(1);
        }
    }

    protected YayaObject(YayaObject parent, String name) throws YayaException {
        synchronized (parent.getYayaState()) {
            this.L = parent.getYayaState();
            if (parent.isTable() || parent.isUserdata()) {
                parent.push();
                this.L.pushString(name);
                this.L.getTable(-2);
                this.L.remove(-2);
                registerValue(-1);
                this.L.pop(1);
            } else {
                throw new YayaException("Object parent should be a table or userdata .");
            }
        }
    }

    protected YayaObject(YayaObject parent, Number name) throws YayaException {
        synchronized (parent.getYayaState()) {
            this.L = parent.getYayaState();
            if (parent.isTable() || parent.isUserdata()) {
                parent.push();
                this.L.pushNumber(name.doubleValue());
                this.L.getTable(-2);
                this.L.remove(-2);
                registerValue(-1);
                this.L.pop(1);
            } else {
                throw new YayaException("Object parent should be a table or userdata .");
            }
        }
    }

    protected YayaObject(YayaObject parent, YayaObject name) throws YayaException {
        if (parent.getYayaState() != name.getYayaState()) {
            throw new YayaException("YayaStates must be the same!");
        }
        synchronized (parent.getYayaState()) {
            if (parent.isTable() || parent.isUserdata()) {
                this.L = parent.getYayaState();
                parent.push();
                name.push();
                this.L.getTable(-2);
                this.L.remove(-2);
                registerValue(-1);
                this.L.pop(1);
            } else {
                throw new YayaException("Object parent should be a table or userdata .");
            }
        }
    }

    protected YayaObject(YayaLib L, int index) {
        synchronized (L) {
            this.L = L;
            registerValue(index);
        }
    }

    public YayaLib getYayaState() {
        return this.L;
    }

    private void registerValue(int index) {
        synchronized (this.L) {
            this.L.pushValue(index);
            this.ref = new Integer(this.L.Lref(-10000));
        }
    }

    protected void finalize() {
        try {
            synchronized (this.L) {
                if (this.L.getCPtrPeer() != 0) {
                    this.L.LunRef(-10000, this.ref.intValue());
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to release object " + this.ref);
        }
    }

    public void push() {
        this.L.rawGetI(-10000, this.ref.intValue());
    }

    public boolean isNil() {
        boolean isNil;
        synchronized (this.L) {
            push();
            isNil = this.L.isNil(-1);
            this.L.pop(1);
        }
        return isNil;
    }

    public boolean isBoolean() {
        boolean isBoolean;
        synchronized (this.L) {
            push();
            isBoolean = this.L.isBoolean(-1);
            this.L.pop(1);
        }
        return isBoolean;
    }

    public boolean isNumber() {
        boolean isNumber;
        synchronized (this.L) {
            push();
            isNumber = this.L.isNumber(-1);
            this.L.pop(1);
        }
        return isNumber;
    }

    public boolean isString() {
        boolean isString;
        synchronized (this.L) {
            push();
            isString = this.L.isString(-1);
            this.L.pop(1);
        }
        return isString;
    }

    public boolean isFunction() {
        boolean isFunction;
        synchronized (this.L) {
            push();
            isFunction = this.L.isFunction(-1);
            this.L.pop(1);
        }
        return isFunction;
    }

    public boolean isJavaObject() {
        boolean isObject;
        synchronized (this.L) {
            push();
            isObject = this.L.isObject(-1);
            this.L.pop(1);
        }
        return isObject;
    }

    public boolean isJavaFunction() {
        boolean isJavaFunction;
        synchronized (this.L) {
            push();
            isJavaFunction = this.L.isJavaFunction(-1);
            this.L.pop(1);
        }
        return isJavaFunction;
    }

    public boolean isTable() {
        boolean isTable;
        synchronized (this.L) {
            push();
            isTable = this.L.isTable(-1);
            this.L.pop(1);
        }
        return isTable;
    }

    public boolean isUserdata() {
        boolean isUserdata;
        synchronized (this.L) {
            push();
            isUserdata = this.L.isUserdata(-1);
            this.L.pop(1);
        }
        return isUserdata;
    }

    public int type() {
        int type;
        synchronized (this.L) {
            push();
            type = this.L.type(-1);
            this.L.pop(1);
        }
        return type;
    }

    public boolean getBoolean() {
        boolean toBoolean;
        synchronized (this.L) {
            push();
            toBoolean = this.L.toBoolean(-1);
            this.L.pop(1);
        }
        return toBoolean;
    }

    public double getNumber() {
        double toNumber;
        synchronized (this.L) {
            push();
            toNumber = this.L.toNumber(-1);
            this.L.pop(1);
        }
        return toNumber;
    }

    public String getString() {
        String yayaLib;
        synchronized (this.L) {
            push();
            yayaLib = this.L.toString(-1);
            this.L.pop(1);
        }
        return yayaLib;
    }

    public Object getObject() throws YayaException {
        Object objectFromUserdata;
        synchronized (this.L) {
            push();
            objectFromUserdata = this.L.getObjectFromUserdata(-1);
            this.L.pop(1);
        }
        return objectFromUserdata;
    }

    public YayaObject getField(String field) throws YayaException {
        return this.L.getYayaObject(this, field);
    }

    public Object[] call(Object[] args, int nres) throws YayaException {
        Object[] objArr;
        int i = 0;
        synchronized (this.L) {
            if (isFunction() || isTable() || isUserdata()) {
                int length;
                int top = this.L.getTop();
                push();
                if (args != null) {
                    length = args.length;
                    while (i < length) {
                        this.L.pushObjectValue(args[i]);
                        i++;
                    }
                } else {
                    length = 0;
                }
                i = this.L.pcall(length, nres, 0);
                if (i != 0) {
                    String yayaLib;
                    if (this.L.isString(-1)) {
                        yayaLib = this.L.toString(-1);
                        this.L.pop(1);
                    } else {
                        yayaLib = "";
                    }
                    if (i == 1) {
                        yayaLib = "Runtime error. " + yayaLib;
                    } else if (i == 4) {
                        yayaLib = "Memory allocation error. " + yayaLib;
                    } else if (i == 5) {
                        yayaLib = "Error while running the error handler function. " + yayaLib;
                    } else {
                        yayaLib = "Yaya Error code " + i + ". " + yayaLib;
                    }
                    throw new YayaException(yayaLib);
                }
                if (nres == -1) {
                    nres = this.L.getTop() - top;
                }
                if (this.L.getTop() - top < nres) {
                    throw new YayaException("Invalid Number of Results .");
                }
                objArr = new Object[nres];
                for (length = nres; length > 0; length--) {
                    objArr[length - 1] = this.L.toJavaObject(-1);
                    this.L.pop(1);
                }
            } else {
                throw new YayaException("Invalid object. Not a function, table or userdata .");
            }
        }
        return objArr;
    }

    public Object call(Object[] args) throws YayaException {
        return call(args, 1)[0];
    }

    public String toString() {
        synchronized (this.L) {
            try {
                if (isNil()) {
                    return "nil";
                } else if (isBoolean()) {
                    r0 = String.valueOf(getBoolean());
                    return r0;
                } else if (isNumber()) {
                    r0 = String.valueOf(getNumber());
                    return r0;
                } else if (isString()) {
                    r0 = getString();
                    return r0;
                } else if (isFunction()) {
                    return "Yaya Function";
                } else if (isJavaObject()) {
                    r0 = getObject().toString();
                    return r0;
                } else if (isUserdata()) {
                    return "Userdata";
                } else if (isTable()) {
                    return "Yaya Table";
                } else if (isJavaFunction()) {
                    return "Java Function";
                } else {
                    return null;
                }
            } catch (YayaException e) {
                return null;
            }
        }
    }

    public Object createProxy(String implem) throws ClassNotFoundException, YayaException {
        Object newProxyInstance;
        synchronized (this.L) {
            if (isTable()) {
                StringTokenizer stringTokenizer = new StringTokenizer(implem, ",");
                Class[] clsArr = new Class[stringTokenizer.countTokens()];
                int i = 0;
                while (stringTokenizer.hasMoreTokens()) {
                    clsArr[i] = Class.forName(stringTokenizer.nextToken());
                    i++;
                }
                newProxyInstance = Proxy.newProxyInstance(getClass().getClassLoader(), clsArr, new YayaInvocationHandler(this));
            } else {
                throw new YayaException("Invalid Object. Must be Table.");
            }
        }
        return newProxyInstance;
    }
}
