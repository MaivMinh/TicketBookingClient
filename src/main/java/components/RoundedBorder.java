package components;

import javax.swing.border.AbstractBorder;
import java.awt.*;

public class RoundedBorder extends AbstractBorder {
  private Color color;
  private int thickness;
  private int arcWidth;
  private int arcHeight;

  public RoundedBorder(Color color, int thickness, int arcWidth, int arcHeight) {
    this.color = color;
    this.thickness = thickness;
    this.arcWidth = arcWidth;
    this.arcHeight = arcHeight;
  }

  @Override
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(color);
    g2.setStroke(new BasicStroke(thickness));
    g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
            width - thickness, height - thickness, arcWidth, arcHeight);
  }

  @Override
  public Insets getBorderInsets(Component c) {
    return new Insets(thickness, thickness, thickness, thickness);
  }

  @Override
  public Insets getBorderInsets(Component c, Insets insets) {
    insets.left = insets.right = insets.top = insets.bottom = thickness;
    return insets;
  }
}
