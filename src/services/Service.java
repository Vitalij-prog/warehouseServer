package services;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Service {
    protected ObjectOutputStream writer = null;
    protected ObjectInputStream reader = null;

    public Service(ObjectOutputStream writer, ObjectInputStream reader) {
        this.writer = writer;
        this.reader = reader;
    }

    public void freeUpResources() {
        if(writer != null) {
            writer = null;
        }
        if(reader != null) {
            reader = null;
        }
    }
    public abstract void execute(String request);
}


