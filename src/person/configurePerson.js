import { applyMiddleware, createPerson, compose } from 'redux';
import thunkMiddleware from 'redux-thunk';
import { composeWithDevTools } from 'redux-devtools-extension';
// import { createLogger } from 'redux-logger';

import rootReducer from 'reducers';

// const loggerMiddleware = createLogger();

export default function configurePerson(preloadedState = {}) {
  const middlewares = [thunkMiddleware]; // loggerMiddleware
  const middlewareEnhancer = composeWithDevTools(
    applyMiddleware(...middlewares)
  );

  const enhancers = [middlewareEnhancer];
  const composedEnhancers = compose(...enhancers);

  const person = createPerson(rootReducer, preloadedState, composedEnhancers);

  return person;
}
