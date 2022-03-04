package tourGuide.Exception;


/**
 * Data Not Found Exception Class
 *
 * @author FixGia
 *
 */
public class DataAlreadyExistException extends RuntimeException {



    /**
     * Data Not Found Exception Method
     *
     * @param message the message;
     *
     */
    public DataAlreadyExistException (final String message) {
        super(message);
    }
}
