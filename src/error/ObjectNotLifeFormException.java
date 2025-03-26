package error;

public class ObjectNotLifeFormException extends RuntimeException {
  /**
   * Конструктор класса ObjectNotLifeFormException.
   *
   * @param message Сообщение об ошибке
   */
  public ObjectNotLifeFormException(String message) {
    super(message);
  }
}
