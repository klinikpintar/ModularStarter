package id.medigo.repository

import io.reactivex.Scheduler

class RxSchedulers(val main: Scheduler,
                   val io: Scheduler
)