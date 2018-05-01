package org.openhr.configuration;

import java.io.IOException;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.resource.PathResourceResolver;

public final class SinglePageAppResourceResolver extends PathResourceResolver {
  @Override
  protected Resource getResource(final String resourcePath, final Resource location)
      throws IOException {
    Resource resource = location.createRelative(resourcePath);
    if (resource.exists() && resource.isReadable()) {
      return resource;
    }
    resource = location.createRelative("index.html");
    if (resource.exists() && resource.isReadable()) {
      return resource;
    }
    return null;
  }
}
