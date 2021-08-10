package Result;

/**
 * This class is the result of a load command.
 */
public class LoadResult {
    /**
     * Contains the error or success message.
     */
    private String message;

    /**
     * keeps track of if the operation was a success or not.
     */
    private boolean success;

    public LoadResult(String error) {
        this.message = error;
        this.success = false;
    }

    /**
     * Creates a new LoadResult.
     * @param message
     * @param success
     */
    public LoadResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
