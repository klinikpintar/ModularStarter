package id.medigo.repository

import io.reactivex.Scheduler

class AppDispatchers(val main: Scheduler,
                     val io: Scheduler
)