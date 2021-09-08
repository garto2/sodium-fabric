package me.jellysquid.mods.sodium.render.chunk.arena.staging;

import me.jellysquid.mods.thingl.buffer.GlBuffer;
import me.jellysquid.mods.thingl.buffer.GlBufferUsage;
import me.jellysquid.mods.thingl.buffer.GlMutableBuffer;
import me.jellysquid.mods.thingl.device.CommandList;

import java.nio.ByteBuffer;

public class FallbackStagingBuffer implements StagingBuffer {
    private final GlMutableBuffer fallbackBufferObject;

    public FallbackStagingBuffer(CommandList commandList) {
        this.fallbackBufferObject = commandList.createMutableBuffer();
    }

    @Override
    public void enqueueCopy(CommandList commandList, ByteBuffer data, GlBuffer dst, long writeOffset) {
        commandList.uploadData(this.fallbackBufferObject, data, GlBufferUsage.STREAM_COPY);
        commandList.copyBufferSubData(this.fallbackBufferObject, dst, 0, writeOffset, data.remaining());
    }

    @Override
    public void flush(CommandList commandList) {
        commandList.allocateStorage(this.fallbackBufferObject, 0L, GlBufferUsage.STREAM_COPY);
    }

    @Override
    public void delete(CommandList commandList) {
        commandList.deleteBuffer(this.fallbackBufferObject);
    }

    @Override
    public void flip() {

    }

    @Override
    public String toString() {
        return "Fallback";
    }
}