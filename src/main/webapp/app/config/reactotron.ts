import { reactotronRedux } from 'reactotron-redux';
import Reactotron from 'reactotron-react-js';

Reactotron.configure({ name: 'React Native Demo' })
  .use(reactotronRedux()) //  <- here i am!
  .connect(); // let's connect!

// Let's clear Reactotron on every time we load the app
Reactotron.clear();

// console.tron = Reactotron
