package com.iupp.warriors.enums

enum class TipoCargo {
    GERENTE {
        override fun permitido(): Boolean {
            return true
        }
    },
    VENDEDOR {
        override fun permitido(): Boolean {
            return false
        }
    };

    abstract fun permitido(): Boolean
}

