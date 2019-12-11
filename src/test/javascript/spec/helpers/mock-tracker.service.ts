import { SpyObject } from './spyobject';
import { TrackerService } from 'app/core/tracker/tracker.service';

export class MockTrackerService extends SpyObject {
  constructor() {
    super(TrackerService);
  }

  connect() {}

  disconnect() {}
}
