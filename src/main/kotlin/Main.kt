private const val MAX_CARD_TRANSFER_PER_DAY = 150_000_00
private const val MAX_CARD_TRANSFER_PER_MONTH = 600_000_00
private const val MAX_VK_PAY_TRANSFER_PER_ONE_TIME = 15_000_00
private const val MAX_VK_PAY_TRANSFER_PER_MONTH = 40_000_00

private const val COMMISSION_MASTERCARD_AND_MAESTRO = 0.6 / 100
private const val OBLIGATORY_COMMISSION_MASTERCARD_AND_MAESTRO = 20_00

private const val COMMISSION_VISA_AND_MIR = 0.75 / 100
private const val MIN_COMMISSION_VISA_AND_MIR = 35_00

fun main() {
    val result = payment(currentTransfer = 14_000_00, previousTranslationsPerMonth = 26_000_01)
    val result2 = payment("Visa", currentTransfer = 4_667_00)
    println(result)
    println(result2)
}

fun commission(
    pay: String = "VK Pay",
    previousTranslations: Int,
    currentTransfer: Int
): String {
    return when (pay) {
        "Mastercard", "Maestro" -> {
            val commission =
                currentTransfer * COMMISSION_MASTERCARD_AND_MAESTRO + OBLIGATORY_COMMISSION_MASTERCARD_AND_MAESTRO
            val noFee = 300_00..75_000_00
            val result =
                if (previousTranslations + currentTransfer !in noFee) "Комиссия составляет $commission коп."
                else "Комиссия не взимается в рамках акции"
            result
        }
        "Visa", "Мир" -> {
            val commission =
                currentTransfer * COMMISSION_VISA_AND_MIR
            val result =
                if (commission < MIN_COMMISSION_VISA_AND_MIR) "Комиссия составляет $MIN_COMMISSION_VISA_AND_MIR коп."
                else "Комиссия составляет $commission коп."
            result
        }
        else -> {
            return "Комиссия не взимается"
        }
    }
}

fun payment(
    pay: String = "VK Pay",
    previousTranslationsPerDay: Int = 0,
    previousTranslationsPerMonth: Int = 0,
    currentTransfer: Int
): String {
    return when (pay) {
        "Mastercard", "Maestro", "Visa", "Мир" -> {
            if (previousTranslationsPerDay + currentTransfer > MAX_CARD_TRANSFER_PER_DAY) {
                "Превышен дневной лимит перевода по карте $pay"
            } else if (previousTranslationsPerMonth + currentTransfer > MAX_CARD_TRANSFER_PER_MONTH) {
                "Превышен ежемесячный лимит перевода по карте $pay"
            } else {
                commission(pay, previousTranslationsPerMonth, currentTransfer)
            }
        }
        "VK Pay" -> {
            if (currentTransfer > MAX_VK_PAY_TRANSFER_PER_ONE_TIME) {
                "Превышен единоразовый лимит перевода через VK Pay"
            } else if (previousTranslationsPerMonth + currentTransfer > MAX_VK_PAY_TRANSFER_PER_MONTH) {
                "Превышен ежемесячный лимит перевода через VK Pay"
            } else {
                commission(pay, previousTranslationsPerMonth, currentTransfer)
            }
        }
        else -> "Данная платежная система не поддерживается"
    }
}
