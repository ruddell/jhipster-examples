import { call, put, select } from 'redux-saga/effects'

import LoginActions from './login.reducer'
import AccountActions from '../../shared/reducers/account.reducer'
import WebsocketService from '../../shared/websockets/websocket.service'

export const selectAuthToken = (state) => state.login.authToken
// attempts to login
export function * login (api, { username, password }) {
  const authObj = {
    username: username,
    password: password,
    rememberMe: true
  }

  const response = yield call(api.login, authObj)

  // success?
  if (response.ok) {
    yield call(api.setAuthToken, response.data.id_token)
    yield put(LoginActions.loginSuccess(response.data.id_token))
    yield put(AccountActions.accountRequest())
    WebsocketService.setToken(response.data.id_token)
    yield put({ type: 'RELOGIN_OK' })
  } else {
    yield put(LoginActions.loginFailure('WRONG'))
  }
}
// attempts to logout
export function * logout (api) {
  yield call(api.removeAuthToken)
  yield put(AccountActions.accountRequest())
  yield put(LoginActions.logoutSuccess())
  yield put({ type: 'RELOGIN_ABORT' })
}
// loads the login
export function * loginLoad (api) {
  const authToken = yield select(selectAuthToken)
  // only set the token if we have it
  if (authToken) {
    yield call(api.setAuthToken, authToken)
    WebsocketService.setToken(authToken)
  }
  yield put(LoginActions.loginLoadSuccess())
}
