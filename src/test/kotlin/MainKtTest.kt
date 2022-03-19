import org.junit.Assert.assertEquals
import org.junit.Test

class MainKtTest {
    @Test
    fun commission_mastercard_and_maestro_up_to_300() {
        // arrange
        val pay = "Mastercard"
        val currentTransfer = 299_00
        val previousTranslations = 0
        val expectedCommission = "Комиссия составляет 2179.4 коп."
        // act
        val actualCommission = commission(pay, previousTranslations, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun commission_mastercard_and_maestro_over_300_up_to_75000() {
        // arrange
        val pay = "Mastercard"
        val currentTransfer = 300_00
        val previousTranslations = 0
        val expectedCommission = "Комиссия не взимается в рамках акции"
        // act
        val actualCommission = commission(pay, previousTranslations, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun commission_mastercard_and_maestro_up_to_75000() {
        // arrange
        val pay = "Mastercard"
        val currentTransfer = 75_000_01
        val previousTranslations = 0
        val expectedCommission = "Комиссия составляет 47000.006 коп."
        // act
        val actualCommission = commission(pay, previousTranslations, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun commission_visa_and_mir_minimal() {
        // arrange
        val pay = "Visa"
        val currentTransfer = 4_665_00
        val previousTranslations = 0
        val expectedCommission = "Комиссия составляет 3500 коп."
        // act
        val actualCommission = commission(pay, previousTranslations, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun commission_visa_and_mir_over_minimal() {
        // arrange
        val pay = "Visa"
        val currentTransfer = 4_667_00
        val previousTranslations = 0
        val expectedCommission = "Комиссия составляет 3500.25 коп."
        // act
        val actualCommission = commission(pay, previousTranslations, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun commission_vk_pay() {
        // arrange
        val pay = "VK Pay"
        val currentTransfer = 15_000_00
        val previousTranslations = 0
        val expectedCommission = "Комиссия не взимается"
        // act
        val actualCommission = commission(pay, previousTranslations, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun payment_by_mastercard_maestro_visa_mir_max_per_day() {
        // arrange
        val pay = "Mastercard"
        val previousTranslationsPerDay = 150_000_00
        val previousTranslationsPerMonth = 0
        val currentTransfer = 1
        val expectedCommission = "Превышен дневной лимит перевода по карте $pay"
        // act
        val actualCommission =
            payment(pay, previousTranslationsPerDay, previousTranslationsPerMonth, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun payment_by_mastercard_maestro_visa_mir_max_per_month() {
        // arrange
        val pay = "Mastercard"
        val previousTranslationsPerDay = 0
        val previousTranslationsPerMonth = 600_000_00
        val currentTransfer = 1
        val expectedCommission = "Превышен ежемесячный лимит перевода по карте $pay"
        // act
        val actualCommission =
            payment(pay, previousTranslationsPerDay, previousTranslationsPerMonth, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun payment_by_vk_pay_max_per_day() {
        // arrange
        val pay = "VK Pay"
        val previousTranslationsPerDay = 0
        val previousTranslationsPerMonth = 0
        val currentTransfer = 15_000_01
        val expectedCommission = "Превышен единоразовый лимит перевода через VK Pay"
        // act
        val actualCommission =
            payment(pay, previousTranslationsPerDay, previousTranslationsPerMonth, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun payment_by_vk_pay_max_per_month() {
        // arrange
        val pay = "VK Pay"
        val previousTranslationsPerDay = 0
        val previousTranslationsPerMonth = 40_000_00
        val currentTransfer = 1
        val expectedCommission = "Превышен ежемесячный лимит перевода через VK Pay"
        // act
        val actualCommission =
            payment(pay, previousTranslationsPerDay, previousTranslationsPerMonth, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }

    @Test
    fun payment_by_chikushka() {
        // arrange
        val pay = "Chikushka"
        val previousTranslationsPerDay = 0
        val previousTranslationsPerMonth = 0
        val currentTransfer = 0
        val expectedCommission = "Данная платежная система не поддерживается"
        // act
        val actualCommission =
            payment(pay, previousTranslationsPerDay, previousTranslationsPerMonth, currentTransfer)
        // assert
        assertEquals(expectedCommission, actualCommission)
    }
}