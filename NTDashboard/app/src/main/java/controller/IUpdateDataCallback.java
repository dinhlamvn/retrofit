package controller;

public interface IUpdateDataCallback {

    void updateOnSucceed(final String response);

    void updateOnFailed(final String message);
}
