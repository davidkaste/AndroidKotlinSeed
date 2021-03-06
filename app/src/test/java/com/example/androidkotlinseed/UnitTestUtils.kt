package com.example.androidkotlinseed

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.example.androidkotlinseed.api.HeroListWrapper
import com.example.androidkotlinseed.injection.DaggerUnitTestApplicationComponent
import com.example.androidkotlinseed.injection.UnitTestApplicationComponent
import com.example.androidkotlinseed.injection.UnitTestApplicationModule
import com.example.androidkotlinseed.injection.UnitTestUseCaseModule
import com.example.androidkotlinseed.repository.mock.Mocks.Companion.mockHeroesJson
import org.mockito.ArgumentCaptor
import org.mockito.Mockito
import org.powermock.api.mockito.PowerMockito
import kotlin.reflect.KClass

@Suppress("MemberVisibilityCanBePrivate")
class UnitTestUtils {

    inline fun <reified T : Any> any() = Mockito.any(T::class.java) ?: createInstance()

    fun getMockApp(): Application {
        val app: Application = PowerMockito.mock(Application::class.java)
        PowerMockito.`when`(app.applicationContext).thenReturn(app)

        return app
    }

    fun createTestApplicationComponent(): UnitTestApplicationComponent {
        return DaggerUnitTestApplicationComponent.builder()
                .applicationModule(UnitTestApplicationModule(getMockApp()))
                .useCaseModule(UnitTestUseCaseModule())
                .build()
    }

    fun createLifecycleOwner(): LifecycleRegistry {
        val lifeCycleOwner: LifecycleOwner = PowerMockito.mock(LifecycleOwner::class.java)
        return LifecycleRegistry(lifeCycleOwner)
    }

    val heroListWrapper = HeroListWrapper.fromJson(mockHeroesJson)
}

/**
 * Creates a [KArgumentCaptor] for given type.
 */
inline fun <reified T : Any> argumentCaptor(): KArgumentCaptor<T> {
    return KArgumentCaptor(ArgumentCaptor.forClass(T::class.java), T::class)
}

class KArgumentCaptor<out T : Any?>(
        private val captor: ArgumentCaptor<T>,
        private val tClass: KClass<*>) {

    /**
     * The first captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val firstValue: T
        get() = captor.firstValue

    /**
     * The second captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val secondValue: T
        get() = captor.secondValue

    /**
     * The third captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val thirdValue: T
        get() = captor.thirdValue

    /**
     * The last captured value of the argument.
     * @throws IndexOutOfBoundsException if the value is not available.
     */
    val lastValue: T
        get() = captor.lastValue

    val allValues: List<T>
        get() = captor.allValues

    @Suppress("UNCHECKED_CAST")
    fun capture(): T {
        return captor.capture() ?: createInstance(tClass) as T
    }
}

val <T> ArgumentCaptor<T>.firstValue: T
    get() = allValues[0]

val <T> ArgumentCaptor<T>.secondValue: T
    get() = allValues[1]

val <T> ArgumentCaptor<T>.thirdValue: T
    get() = allValues[2]

val <T> ArgumentCaptor<T>.lastValue: T
    get() = allValues.last()

inline fun <reified T : Any> createInstance(): T {
    return createInstance(T::class)
}

@Suppress("UNUSED_PARAMETER")
fun <T : Any> createInstance(kClass: KClass<T>): T {
    return castNull()
}

/**
 * Uses a quirk in the bytecode generated by Kotlin
 * to cast null to a non-null type.
 *
 * See https://youtrack.jetbrains.com/issue/KT-8135.
 */
@Suppress("UNCHECKED_CAST")
private fun <T> castNull(): T = null as T
