package com.iupp.warriors

import io.micronaut.runtime.Micronaut.*
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("com.iupp.warriors")
		.start()
}

