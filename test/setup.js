import { configure } from 'enzyme'
import Adapter from 'enzyme-adapter-react-16'
import Config from '../app/config/debug-config'

Config.useReactotron = false
configure({ adapter: new Adapter() })

// Mock your external modules here if needed
jest
  .mock('react-native-navigation', () => {
    return { Navigation: { showModal: jest.fn((url) => { return [] }) } }
  })
  .mock('react-native-vector-icons/FontAwesome', () => 'Icon')
  .mock('@storybook/react-native', () => {
    return { getStorybookUI: jest.fn(), configure: jest.fn() }
  })
console.tron = { log: () => {}, display: () => {} }
