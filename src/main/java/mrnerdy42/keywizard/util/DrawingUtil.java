package mrnerdy42.keywizard.util;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

public class DrawingUtil {
	public static void fill(MatrixStack matrices, float x1, float y1, float x2, float y2, int color) {
		Matrix4f matrix = matrices.peek().getModel();
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
		RenderSystem.enableBlend();
		RenderSystem.disableTexture();
		RenderSystem.defaultBlendFunc();
		bufferBuilder.begin(7, VertexFormats.POSITION_COLOR);
		bufferBuilder.vertex(matrix, x1, y2, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(matrix, x2, y2, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(matrix, x2, y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.vertex(matrix, x1, y1, 0.0F).color(g, h, k, f).next();
		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);
		RenderSystem.enableTexture();
		RenderSystem.disableBlend();
	}
	
	public static void drawHorizontalLine(MatrixStack matrices, float x1, float x2, float y, int color) {
		if (x2 < x1) {
			float i = x1;
			x1 = x2;
			x2 = i;
		}

		fill(matrices, x1, y, x2 + 1, y + 1, color);
	}
	
	public static void drawVerticalLine(MatrixStack matrices, float x, float y1, float y2, int color) {
		if (y2 < y1) {
			float i = y1;
			y1 = y2;
			y2 = i;
		}

		fill(matrices, x, y1 + 1, x + 1, y2, color);
	}
	
	public static void drawNoFillRect(MatrixStack matrices, float left, float top, float right, float bottom, int color) {
		drawHorizontalLine(matrices, left, right, top, color);
		drawHorizontalLine(matrices, left, right, bottom, color);
		drawVerticalLine(matrices, left, top, bottom, color);
		drawVerticalLine(matrices, right, top, bottom, color);
	}
}
