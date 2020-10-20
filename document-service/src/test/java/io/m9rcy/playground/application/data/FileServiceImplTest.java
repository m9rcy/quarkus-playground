package io.m9rcy.playground.application.data;

import io.quarkus.test.junit.QuarkusTest;
import io.vertx.mutiny.core.Vertx;

import javax.inject.Inject;

@QuarkusTest
class FileServiceImplTest {

    private static final String DEFAULT_FILE_PERMS = "rw-r--r--";

    @Inject
    private Vertx vertx;
    private String pathSep;
    private String testDir;

}