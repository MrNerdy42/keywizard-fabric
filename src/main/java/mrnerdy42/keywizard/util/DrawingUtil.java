package mrnerdy42.keywizard.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

public class DrawingUtil {
	public static void fill(float x1, float y1, float x2, float y2, int color) {
		float j;
		if (x1 < x2) {
			j = x1;
			x1 = x2;
			x2 = j;
		}

		if (y1 < y2) {
			j = y1;
			y1 = y2;
			y2 = j;
		}

		float f = (float)(color >> 24 & 255) / 255.0F;
		float g = (float)(color >> 16 & 255) / 255.0F;
		float h = (float)(color >> 8 & 255) / 255.0F;
		float k = (float)(color & 255) / 255.0F;
		BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture();
		GlStateManager.disableBlend();
		bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(x1, y2, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(x2, y2, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(x2, y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(x1, y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.end();
		BufferRenderer bufferRenderer = new BufferRenderer();
		bufferRenderer.draw(bufferBuilder);
		GlStateManager.enableTexture();
		GlStateManager.disableBlend();
	}
	
	public static void drawHorizontalLine(float x1, float x2, float y, int color) {
		if (x2 < x1) {
			float i = x1;
			x1 = x2;
			x2 = i;
		}

		fill(x1, y, x2 + 1, y + 1, color);
	}
	
	public static void drawVerticalLine(float x, float y1, float y2, int color) {
		if (y2 < y1) {
			float i = y1;
			y1 = y2;
			y2 = i;
		}

		fill(x, y1 + 1, x + 1, y2, color);
	}
	
	public static void drawNoFillRect(float left, float top, float right, float bottom, int color) {
		drawHorizontalLine(left, right, top, color);
		drawHorizontalLine(left, right, bottom, color);
		drawVerticalLine(left, top, bottom, color);
		drawVerticalLine(right, top, bottom, color);
	}
}
