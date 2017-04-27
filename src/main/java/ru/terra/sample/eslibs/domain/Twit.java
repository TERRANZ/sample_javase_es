package ru.terra.sample.eslibs.domain;

import io.searchbox.annotations.JestId;

import java.io.Serializable;
import java.util.UUID;

public class Twit implements Serializable {
    private static final long serialVersionUID = 1L;
    @JestId
    private String guid;
    private String text;
    private String uid;

    public Twit() {
        this.guid = UUID.randomUUID().toString();
    }

    public Twit(String guid, String text, String userId) {
        this.guid = guid;
        this.text = text;
        this.uid = userId;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
