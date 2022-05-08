package sourabh.pal.cricket.common.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import io.reactivex.disposables.CompositeDisposable
import sourabh.pal.cricket.common.data.PlayerRepositoryIml
import sourabh.pal.cricket.common.domain.repositories.PlayerRepository
import sourabh.pal.cricket.common.utils.CoroutineDispatchersProvider
import sourabh.pal.cricket.common.utils.DispatchersProvider

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class ActivityRetainedScope {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindPlayerRepository(repository: PlayerRepositoryIml): PlayerRepository


    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
            DispatchersProvider

    companion object {
        @Provides
        fun provideCompositeDisposable() = CompositeDisposable()
    }
}