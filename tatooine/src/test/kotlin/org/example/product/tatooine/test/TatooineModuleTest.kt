package org.example.product.tatooine.test

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertSame

class TatooineModuleTest {
    @Test
    fun testModule() {
        assertSame("Junit4", "Junit" + (2 + 2))
    }
}