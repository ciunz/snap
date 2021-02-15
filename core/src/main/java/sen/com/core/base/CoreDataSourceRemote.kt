package sen.com.core.base

import retrofit2.Response
import sen.com.abstraction.bases.data.dataSource.BaseRemoteDataSource
import sen.com.abstraction.bases.data.Result
import sen.com.core.utils.responseParser.RetrofitResponseParser

/**
 * Created by korneliussendy on 28/09/20,
 * at 13.43.
 * Snap
 */
abstract class CoreDataSourceRemote : BaseRemoteDataSource() {

    protected suspend fun <T> mapRetrofitResponse(call: suspend () -> Response<T>): Result<T?> =
        getResult(RetrofitResponseParser, call)

}