package net.lax1dude.eaglercraft.internal.teavm;

import net.lax1dude.eaglercraft.opengl.EaglercraftGPU;
import net.lax1dude.eaglercraft.opengl.ImageData;

import static net.lax1dude.eaglercraft.internal.PlatformOpenGL.*;
import static net.lax1dude.eaglercraft.opengl.RealOpenGLEnums.*;

import net.lax1dude.eaglercraft.internal.IVertexArrayGL;
import net.lax1dude.eaglercraft.internal.IBufferGL;
import net.lax1dude.eaglercraft.internal.IProgramGL;
import net.lax1dude.eaglercraft.internal.IShaderGL;
import net.lax1dude.eaglercraft.internal.ITextureGL;
import net.lax1dude.eaglercraft.internal.PlatformAssets;
import net.lax1dude.eaglercraft.internal.PlatformInput;
import net.lax1dude.eaglercraft.internal.PlatformRuntime;
import net.lax1dude.eaglercraft.internal.buffer.ByteBuffer;
import net.lax1dude.eaglercraft.internal.buffer.FloatBuffer;
import net.lax1dude.eaglercraft.internal.buffer.IntBuffer;
import net.lax1dude.eaglercraft.Base64;
import net.lax1dude.eaglercraft.EagUtils;

/**
 * Copyright (c) 2022 lax1dude. All Rights Reserved.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * 
 */
public class EarlyLoadScreen {

	private static IBufferGL vbo = null;
	private static IProgramGL program = null;

	private static ITextureGL pressDeleteTexture = null;
	private static IProgramGL pressDeleteProgram = null;

	private static ITextureGL finalTexture = null;

	private static final String loadScreen = "";
	private static final String pressDeleteText = "";
	private static final String enableScreen = "";

	public static void paintScreen(int glesVers, boolean vaos) {
		boolean gles3 = glesVers >= 300;
		
		// create textures:
		
		ITextureGL tex = _wglGenTextures();
		_wglActiveTexture(GL_TEXTURE0);
		_wglBindTexture(GL_TEXTURE_2D, tex);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		ImageData img = PlatformAssets.loadImageFile(Base64.decodeBase64(loadScreen));
		ByteBuffer upload = PlatformRuntime.allocateByteBuffer(192*192*4);
		IntBuffer pixelUpload = upload.asIntBuffer();
		pixelUpload.put(img.pixels);
		pixelUpload.flip();
		_wglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 192, 192, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelUpload);
		
		pressDeleteTexture = _wglGenTextures();
		_wglBindTexture(GL_TEXTURE_2D, pressDeleteTexture);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		
		pixelUpload.clear();
		img = PlatformAssets.loadImageFile(Base64.decodeBase64(pressDeleteText));
		pixelUpload.put(img.pixels);
		pixelUpload.flip();
		_wglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 384, 16, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixelUpload);
		
		// create vertex buffer:
		
		FloatBuffer vertexUpload = upload.asFloatBuffer();
		vertexUpload.clear();
		vertexUpload.put(0.0f); vertexUpload.put(0.0f);
		vertexUpload.put(0.0f); vertexUpload.put(1.0f);
		vertexUpload.put(1.0f); vertexUpload.put(0.0f);
		vertexUpload.put(1.0f); vertexUpload.put(0.0f);
		vertexUpload.put(0.0f); vertexUpload.put(1.0f);
		vertexUpload.put(1.0f); vertexUpload.put(1.0f);
		vertexUpload.flip();
			
		vbo = _wglGenBuffers();
		_wglBindBuffer(GL_ARRAY_BUFFER, vbo);
		_wglBufferData(GL_ARRAY_BUFFER, vertexUpload, GL_STATIC_DRAW);
		
		PlatformRuntime.freeByteBuffer(upload);

		// compile the splash shader:
		
		IShaderGL vert = _wglCreateShader(GL_VERTEX_SHADER);
		_wglShaderSource(vert, gles3
				? "#version 300 es\nprecision mediump float; layout(location = 0) in vec2 a_pos; out vec2 v_pos; void main() { gl_Position = vec4(((v_pos = a_pos) - 0.5) * vec2(2.0, -2.0), 0.0, 1.0); }"
				: "#version 100\nprecision mediump float; attribute vec2 a_pos; varying vec2 v_pos; void main() { gl_Position = vec4(((v_pos = a_pos) - 0.5) * vec2(2.0, -2.0), 0.0, 1.0); }");
		_wglCompileShader(vert);
		
		IShaderGL frag = _wglCreateShader(GL_FRAGMENT_SHADER);
		_wglShaderSource(frag, gles3
				? "#version 300 es\nprecision mediump float; precision mediump sampler2D; in vec2 v_pos; layout(location = 0) out vec4 fragColor; uniform sampler2D tex; uniform vec2 aspect; void main() { fragColor = vec4(textureLod(tex, clamp(v_pos * aspect - ((aspect - 1.0) * 0.5), 0.02, 0.98), 0.0).rgb, 1.0); }"
				: "#version 100\nprecision mediump float; precision mediump sampler2D; varying vec2 v_pos; uniform sampler2D tex; uniform vec2 aspect; void main() { gl_FragColor = vec4(texture2D(tex, clamp(v_pos * aspect - ((aspect - 1.0) * 0.5), 0.02, 0.98)).rgb, 1.0); }");
		_wglCompileShader(frag);
		
		program = _wglCreateProgram();
		
		_wglAttachShader(program, vert);
		_wglAttachShader(program, frag);
		if(!gles3) {
			_wglBindAttribLocation(program, 0, "a_pos");
		}
		_wglLinkProgram(program);
		_wglDetachShader(program, vert);
		_wglDetachShader(program, frag);
		_wglDeleteShader(vert);
		_wglDeleteShader(frag);
		
		_wglUseProgram(program);
		_wglUniform1i(_wglGetUniformLocation(program, "tex"), 0);

		int width = PlatformInput.getWindowWidth();
		int height = PlatformInput.getWindowHeight();
		float x, y;
		if(width > height) {
			x = (float)width / (float)height;
			y = 1.0f;
		}else {
			x = 1.0f;
			y = (float)height / (float)width;
		}
		
		_wglActiveTexture(GL_TEXTURE0);
		_wglBindTexture(GL_TEXTURE_2D, tex);
		
		_wglViewport(0, 0, width, height);
		_wglClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		_wglClear(GL_COLOR_BUFFER_BIT);

		_wglUseProgram(program);
		_wglUniform2f(_wglGetUniformLocation(program, "aspect"), x, y);
		
		IVertexArrayGL vao = null;
		if(vaos) {
			vao = _wglGenVertexArrays();
			_wglBindVertexArray(vao);
		}
		_wglEnableVertexAttribArray(0);
		_wglVertexAttribPointer(0, 2, GL_FLOAT, false, 8, 0);
		_wglDrawArrays(GL_TRIANGLES, 0, 6);
		
		_wglDisableVertexAttribArray(0);
		
		PlatformInput.update();
		EagUtils.sleep(50); // allow webgl to flush

		_wglUseProgram(null);
		_wglBindBuffer(GL_ARRAY_BUFFER, null);
		_wglBindTexture(GL_TEXTURE_2D, null);
		_wglDeleteTextures(tex);
		if(vaos) {
			_wglDeleteVertexArrays(vao);
		}
	}

	public static void paintEnable(boolean vaos) {
		
		ITextureGL tex = _wglGenTextures();
		_wglActiveTexture(GL_TEXTURE0);
		_wglBindTexture(GL_TEXTURE_2D, tex);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		ImageData img = PlatformAssets.loadImageFile(Base64.decodeBase64(enableScreen));
		IntBuffer upload = PlatformRuntime.allocateIntBuffer(128*128);
		upload.put(img.pixels);
		upload.flip();
		_wglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 128, 128, 0, GL_RGBA, GL_UNSIGNED_BYTE, upload);
		
		PlatformRuntime.freeIntBuffer(upload);
		
		_wglUseProgram(program);

		int width = PlatformInput.getWindowWidth();
		int height = PlatformInput.getWindowHeight();
		float x, y;
		if(width > height) {
			x = (float)width / (float)height;
			y = 1.0f;
		}else {
			x = 1.0f;
			y = (float)height / (float)width;
		}
		
		_wglActiveTexture(GL_TEXTURE0);
		_wglBindTexture(GL_TEXTURE_2D, tex);
		
		_wglViewport(0, 0, width, height);
		_wglClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		_wglClear(GL_COLOR_BUFFER_BIT);
		
		_wglUniform2f(_wglGetUniformLocation(program, "aspect"), x, y);

		IVertexArrayGL vao = null;
		if(vaos) {
			vao = _wglGenVertexArrays();
			_wglBindVertexArray(vao);
		}
		_wglBindBuffer(GL_ARRAY_BUFFER, vbo);
		_wglEnableVertexAttribArray(0);
		_wglVertexAttribPointer(0, 2, GL_FLOAT, false, 8, 0);
		_wglDrawArrays(GL_TRIANGLES, 0, 6);
		
		_wglDisableVertexAttribArray(0);
		
		PlatformInput.update();
		EagUtils.sleep(50); // allow webgl to flush

		_wglUseProgram(null);
		_wglBindBuffer(GL_ARRAY_BUFFER, null);
		_wglBindTexture(GL_TEXTURE_2D, null);
		_wglDeleteTextures(tex);
		if(vaos) {
			_wglDeleteVertexArrays(vao);
		}
		
	}

	public static void loadFinal(byte[] image) {
		ImageData img = PlatformAssets.loadImageFile(image);
		if(img == null) {
			return;
		}
		finalTexture = _wglGenTextures();
		_wglActiveTexture(GL_TEXTURE0);
		_wglBindTexture(GL_TEXTURE_2D, finalTexture);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
		_wglTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
		IntBuffer upload = PlatformRuntime.allocateIntBuffer(img.width * img.height);
		upload.put(img.pixels);
		upload.flip();
		_wglTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, img.width, img.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, upload);
		PlatformRuntime.freeIntBuffer(upload);
	}

	public static void paintFinal(boolean vaos, boolean softVAOs) {
		if(finalTexture == null) return;
		
		_wglBindTexture(GL_TEXTURE_2D, finalTexture);
		_wglUseProgram(program);

		int width = PlatformInput.getWindowWidth();
		int height = PlatformInput.getWindowHeight();
		float x, y;
		if(width > height) {
			x = (float)width / (float)height;
			y = 1.0f;
		}else {
			x = 1.0f;
			y = (float)height / (float)width;
		}
		
		_wglActiveTexture(GL_TEXTURE0);
		_wglBindTexture(GL_TEXTURE_2D, finalTexture);
		
		_wglViewport(0, 0, width, height);
		_wglClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		_wglClear(GL_COLOR_BUFFER_BIT);
		
		_wglUniform2f(_wglGetUniformLocation(program, "aspect"), x, y);

		IVertexArrayGL vao = null;
		if(vaos) {
			if(softVAOs) {
				vao = EaglercraftGPU.createGLVertexArray();
				EaglercraftGPU.bindGLVertexArray(vao);
			}else {
				vao = _wglGenVertexArrays();
				_wglBindVertexArray(vao);
			}
		}
		if(vaos && softVAOs) {
			EaglercraftGPU.bindVAOGLArrayBuffer(vbo);
			EaglercraftGPU.enableVertexAttribArray(0);
			EaglercraftGPU.vertexAttribPointer(0, 2, GL_FLOAT, false, 8, 0);
			EaglercraftGPU.drawArrays(GL_TRIANGLES, 0, 6);
		}else {
			_wglBindBuffer(GL_ARRAY_BUFFER, vbo);
			_wglEnableVertexAttribArray(0);
			_wglVertexAttribPointer(0, 2, GL_FLOAT, false, 8, 0);
			_wglDrawArrays(GL_TRIANGLES, 0, 6);
		}

		if(!softVAOs) {
			_wglDisableVertexAttribArray(0);
		}
		
		PlatformInput.update();
		EagUtils.sleep(50); // allow webgl to flush

		_wglUseProgram(null);
		if(!(vaos && softVAOs)) {
			_wglBindBuffer(GL_ARRAY_BUFFER, null);
		}
		_wglBindTexture(GL_TEXTURE_2D, null);
		if(softVAOs) {
			EaglercraftGPU.clearCurrentBinding(EaglercraftGPU.CLEAR_BINDING_ACTIVE_TEXTURE | EaglercraftGPU.CLEAR_BINDING_TEXTURE0);
		}
		if(vaos) {
			if(softVAOs) {
				EaglercraftGPU.destroyGLVertexArray(vao);
			}else {
				_wglDeleteVertexArrays(vao);
			}
		}
	}

	public static void destroy() {
		if(vbo != null) {
			_wglDeleteBuffers(vbo);
			vbo = null;
		}
		if(program != null) {
			_wglDeleteProgram(program);
			program = null;
		}
		if(pressDeleteTexture != null) {
			_wglDeleteTextures(pressDeleteTexture);
			pressDeleteTexture = null;
		}
		if(pressDeleteProgram != null) {
			_wglDeleteProgram(pressDeleteProgram);
			pressDeleteProgram = null;
		}
		if(finalTexture != null) {
			_wglDeleteTextures(finalTexture);
			finalTexture = null;
		}
	}

}
