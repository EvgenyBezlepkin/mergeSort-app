public interface Presenter {
    void showError(String message);
    void showError(Throwable e);
    void showInfo(String message);

}
