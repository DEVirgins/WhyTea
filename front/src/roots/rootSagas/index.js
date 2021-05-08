import {
    all,
    call,
} from 'redux-saga/effects';

const sagaList = [];

export default function* watchRootSagas() {
    yield all(sagaList.map((saga) => call(saga)))
}