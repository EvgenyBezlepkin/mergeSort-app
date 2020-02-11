public class ConsolePresenter implements Presenter{
    public void showError(String message) {
        System.err.println(message);
    }

    public void showError(Throwable e) {
        System.err.println(e.getMessage());
    }

    public void showInfo(String message) {
        System.out.println(message);
    }
}
