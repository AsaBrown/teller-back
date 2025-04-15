package com.browna.teller_back.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        StringBuilder builder = new StringBuilder();
        RepeatableContentCachingRequestWrapper wrapper = new RepeatableContentCachingRequestWrapper(request);

        builder.append("Request:\n");
        builder.append(wrapper.getMethod()).append(" ").append(wrapper.getRequestURI()).append("\n");
        wrapper.getHeaderNames().asIterator().forEachRemaining(header -> {
            builder.append(header).append(": ").append(wrapper.getHeader(header)).append("\n");
        });

        builder.append("\n");
        builder.append(wrapper.getContentAsString());
        log.info(builder.toString());

        filterChain.doFilter(wrapper, response);
    }
}

class RepeatableContentCachingRequestWrapper extends ContentCachingRequestWrapper {

    public RepeatableContentCachingRequestWrapper(HttpServletRequest request) {
        super(request);
        try {
            StreamUtils.drain(super.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new ByteServletInputStream(getContentAsByteArray());
    }

    private static class ByteServletInputStream extends ServletInputStream {
        private final InputStream stream;

        public ByteServletInputStream(byte[] content) {
            this.stream = new ByteArrayInputStream(content);
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            // Not implemented
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public void close() throws IOException {
            stream.close();
        }
    }
}
