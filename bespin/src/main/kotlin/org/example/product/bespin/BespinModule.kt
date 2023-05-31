package org.example.product.bespin

import org.apache.velocity.io.VelocityWriter
import org.example.product.corellia.CorelliaModule
import org.example.product.coruscant.CoruscantModule
import java.io.FileWriter

class BespinModule {

    fun doThings(): CorelliaModule {
        CoruscantModule()
        VelocityWriter(FileWriter(System.getProperty("java.io.tmpdir") + "/dummy.out"))
        return CorelliaModule()
    }
}