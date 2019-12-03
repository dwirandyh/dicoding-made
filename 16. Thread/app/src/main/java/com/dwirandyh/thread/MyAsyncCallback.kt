package com.dwirandyh.thread

interface MyAsyncCallback {
    fun onPreExecute()
    fun onPostExecute(text: String)
}