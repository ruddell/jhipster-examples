import React from 'react';

import { connect } from 'react-redux';

export interface ITrackerPageProps {
  activities: any[];
}

export const TrackerPage = ({ activities }: ITrackerPageProps) => (
  <div>
    <h2>Real-time user activities</h2>
    <table className="table table-sm table-striped table-bordered">
      <thead>
        <tr>
          <th>
            <span>User</span>
          </th>
          <th>
            <span>IP Address</span>
          </th>
          <th>
            <span>Current page</span>
          </th>
          <th>
            <span>Time</span>
          </th>
        </tr>
      </thead>
      <tbody>
        {activities.map((activity, i) => (
          <tr key={`log-row-${i}`}>
            <td>{activity.userLogin}</td>
            <td>{activity.ipAddress}</td>
            <td>{activity.page}</td>
            <td>{activity.time}</td>
          </tr>
        ))}
      </tbody>
    </table>
  </div>
);

const mapStateToProps = ({ administration }) => ({
  activities: administration.tracker.activities,
});

export default connect(mapStateToProps)(TrackerPage);
