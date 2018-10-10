package com.wenjian.wanandroid.model

import com.wenjian.wanandroid.base.BaseViewModel
import com.wenjian.wanandroid.model.data.DataRepository

/**
 * Description: DataViewModel
 * Date: 2018/10/10
 *
 * @author jian.wen@ubtrobot.com
 */

open class DataViewModel : BaseViewModel<DataRepository>(DataRepository.getInstance())