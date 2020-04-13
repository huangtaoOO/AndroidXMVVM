package com.tao.androidx_mvvm

import com.tao.androidx_mvvm.basis.viewmodel.BaseViewModel
import com.tao.androidx_mvvm.viewmodel.ViewModelOfSQL
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
        print(BaseViewModel::class.java.isAssignableFrom(ViewModelOfSQL::class.java));
    }
}
