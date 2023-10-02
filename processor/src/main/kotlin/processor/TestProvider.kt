package processor

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated

class TestProcessor(private val codeGenerator: CodeGenerator) : SymbolProcessor {
    var invoked = false
    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) {
            return emptyList()
        }

        codeGenerator.createNewFile(Dependencies.ALL_FILES, "app.generated", "Hello")
            .writer()
            .use {
                it.write(
                    """
                        package app.generated                        
                        import app.name
                        
                        fun hello() {
                            println("Hello2 ${'$'}name")
                        }
                    """.trimIndent()
                )
            }
        invoked = true
        return emptyList()
    }
}


class TestProcessorProvider : SymbolProcessorProvider {
    override fun create(
        environment: SymbolProcessorEnvironment
    ): SymbolProcessor {
        return TestProcessor(environment.codeGenerator)
    }
}


