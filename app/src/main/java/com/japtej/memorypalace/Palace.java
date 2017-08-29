package com.japtej.memorypalace;

/**
 * Created by JaptejSingh on 04-08-2017.
 */

public class Palace {

    int _id;
    String _name;

    public Palace() {
    }

    public Palace(int _id) {
        this._id = _id;
    }

    public Palace(String _name) {
        this._name = _name;
    }

    public Palace(int _id, String _name) {
        this._id = _id;
        this._name = _name;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
