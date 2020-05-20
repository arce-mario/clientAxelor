package com.uca.isi.axelor.data;

import com.uca.isi.axelor.entities.User;

public class UserData {
    private int status;
    private int offset;
    private int total;
    private User[] data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public User[] getData() {
        return data;
    }

    public void setData(User[] data) {
        this.data = data;
    }
}
