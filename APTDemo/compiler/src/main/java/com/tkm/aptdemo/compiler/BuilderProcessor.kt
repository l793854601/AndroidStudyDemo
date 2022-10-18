package com.tkm.aptdemo.compiler

import com.bennyhuo.aptutils.AptContext
import com.bennyhuo.aptutils.logger.Logger
import com.tkm.aptdemo.annotations.Builder
import com.tkm.aptdemo.annotations.Optional
import com.tkm.aptdemo.annotations.Required
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

class BuilderProcessor: AbstractProcessor() {
    private val supportedAnnotations = setOf(Builder::class.java, Required::class.java, Optional::class.java)

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        AptContext.init(processingEnv)

    }

    override fun process(elements: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
        roundEnv.getElementsAnnotatedWith(Builder::class.java).forEach {
            Logger.warn(it, it.simpleName.toString())
            processingEnv.messager.printMessage(Diagnostic.Kind.NOTE, it.simpleName)
        }
        return true
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return supportedAnnotations.map { it.canonicalName }.toMutableSet()
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return processingEnv.sourceVersion
    }
}