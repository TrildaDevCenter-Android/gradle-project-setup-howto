package org.example.product.coruscant.test

import org.assertj.core.api.Assertions
import org.example.product.coruscant.CoruscantModule
import org.junit.jupiter.api.Test
import java.io.BufferedReader
import java.io.InputStreamReader

class CoruscantModuleTest {

    @Test
    fun testCoruscantModule() {
        Assertions.assertThat(CoruscantModule()).isNotEqualTo(null)
        CoruscantModuleTest::class.java.getResourceAsStream("testHello.txt").use { hello ->
            val message = BufferedReader(InputStreamReader(hello)).readLine()
            Assertions.assertThat(message).isEqualTo("TEST HELLO!")
        }
    }
}