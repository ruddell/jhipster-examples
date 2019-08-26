import FixtureAPI from '../../../../app/shared/services/fixture-api'
import { call, put, select } from 'redux-saga/effects'

import { login, logout, loginLoad, selectAuthToken } from '../../../../app/modules/login/login.sagas'
import LoginActions from '../../../../app/modules/login/login.reducer'
import AccountActions from '../../../../app/shared/reducers/account.reducer'

const stepper = (fn) => (mock) => fn.next(mock).value

test('login success path', () => {
  const authObj = {
    username: 'user',
    password: 'user',
    rememberMe: true
  }
  const response = FixtureAPI.login(authObj)
  const step = stepper(login(FixtureAPI, authObj))

  expect(step(response)).toEqual(call(FixtureAPI.login, { username: 'user', password: 'user', rememberMe: true }))
  // Set the auth token on the API
  expect(step(response)).toEqual(call(FixtureAPI.setAuthToken, response.data.id_token))
  // Store the auth token in redux
  expect(step(response)).toEqual(put(LoginActions.loginSuccess(response.data.id_token)))
  // Request the account details
  expect(step()).toEqual(put(AccountActions.accountRequest()))
  // Close the relogin popup if needed
  expect(step()).toEqual(put({ type: 'RELOGIN_OK' }))
})
test('login failure path', () => {
  const authObj = {
    username: 'user',
    password: 'user2',
    rememberMe: true
  }
  const response = FixtureAPI.login(authObj)
  const step = stepper(login(FixtureAPI, { username: 'user', password: 'user2' }))

  // Attempt to login and fail
  expect(step(response)).toEqual(call(FixtureAPI.login, authObj))
  // Send the error
  expect(step(response)).toEqual(put(LoginActions.loginFailure('WRONG')))
})
test('login load path with no token', () => {
  const step = stepper(loginLoad(FixtureAPI))
  // Attempt to select the token
  expect(step()).toEqual(select(selectAuthToken))
  // No token was found so call success
  expect(step()).toEqual(put(LoginActions.loginLoadSuccess()))
})
test('login load path with a token', () => {
  const accessToken = 'sample-access-token'
  const step = stepper(loginLoad(FixtureAPI))
  // Select the token from redux and set it
  expect(step(accessToken)).toEqual(select(selectAuthToken))
  expect(step(accessToken)).toEqual(call(FixtureAPI.setAuthToken, accessToken))
  FixtureAPI.setAuthToken(accessToken)
  // The token has been set so call success
  expect(step()).toEqual(put(LoginActions.loginLoadSuccess()))
})
test('logout success path', () => {
  const step = stepper(logout(FixtureAPI))
  expect(step()).toEqual(call(FixtureAPI.removeAuthToken))
  FixtureAPI.removeAuthToken()
  // Reset the account and logout
  expect(step()).toEqual(put(AccountActions.accountRequest()))
  expect(step()).toEqual(put(LoginActions.logoutSuccess()))
  expect(step()).toEqual(put({ type: 'RELOGIN_ABORT' }))
})
test('selects the auth token', () => {
  const state = { login: { authToken: 'hi' } }
  // Retrieve the API token
  expect(selectAuthToken(state)).toEqual('hi')
})
