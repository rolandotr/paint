package paint.gui;

/** comment 2007-Feb-12 Trujillo
 * Esta es para definir la comunicacion entre el Scheme y los PaintTools.
 */
public interface PaintToolListener {

  /** comment 2007-Feb-12 Trujillo
   * Usada para cuando se levanta el pincel, o bien se termina de pintar.
   */
  public void closed();

  /** comment 2006-Sep-17 Trujillo
   * Usada solo para cuando se pinta algo y se le deba notificar a la escucha
   */
  public void imageChange();

}
