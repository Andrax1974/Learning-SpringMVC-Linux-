package com.xantrix.webapp.config;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class MultipartResolverConfig implements ResourceLoaderAware
{
	private final long MAX_UPLOAD_SIZE = FileUtils.ONE_MB * 256;
	private final long MAX_UPLOAD_SIZE_PER_FILE = FileUtils.ONE_MB * 3;
	private final String DEFAULT_ENCODING = "UTF-8";
	private Logger LOG = LoggerFactory.getLogger(getClass());
	private final String UPLOAD_TEMP_DIR = "/tmp";
	private ResourceLoader resourceLoader;

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader)
	{
		 this.resourceLoader = resourceLoader;
	}

	@Bean(name = "filterMultipartResolver")
	public CommonsMultipartResolver multipartResolver()
	{
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding(DEFAULT_ENCODING);
		resolver.setMaxUploadSize(MAX_UPLOAD_SIZE); 
		resolver.setMaxUploadSizePerFile(MAX_UPLOAD_SIZE_PER_FILE);
		resolver.setMaxInMemorySize(0);

		try
		{
			Resource uploadTempDir = resourceLoader.getResource(UPLOAD_TEMP_DIR);
			resolver.setUploadTempDir(uploadTempDir);

			LOG.debug(String.format("UPLOAD TEMP DIR = %s", uploadTempDir));
		} 
		catch (IOException e)
		{
			LOG.error(String.format("ERRORE TEMP DIR", UPLOAD_TEMP_DIR), e);
		}

		return resolver;
	}

}
