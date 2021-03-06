package br.com.rsicarelli.rxfirebaselogin.feature.login.jobs

import android.content.Intent
import br.com.rsicarelli.rxfirebaselogin.feature.login.LoginState
import br.com.rsicarelli.rxfirebaselogin.feature.login.google.GoogleFirebaseAuth
import io.fluent.StateType
import io.fluent.rx.RxJob
import io.fluent.rx.RxStore
import io.reactivex.Completable
import javax.inject.Inject

class DoGoogleLoginJob @Inject constructor(
    private val store: RxStore<LoginState>,
    private val firebase: GoogleFirebaseAuth
) : RxJob<Intent>() {

  override fun bind(input: Intent): Completable {
    return firebase.firebaseAuthWith(intent = input)
        .doOnSubscribe { store.update { setType(StateType.Loading) } }
        .doOnSuccess { store.update { setType(StateType.Success) } }
        .doOnError { store.update { setType(StateType.Error) } }
        .toCompletable()
        .onErrorComplete()
  }
}
