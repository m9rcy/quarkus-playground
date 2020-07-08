package io.m9rcy.playground;

import io.m9rcy.playground.web.resource.CreditCardResourceTest;
import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeExampleResourceIT extends CreditCardResourceTest {

    // Execute the same tests but in native mode.
}