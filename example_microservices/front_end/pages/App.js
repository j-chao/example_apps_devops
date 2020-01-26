import React, { Component } from "react";
import ReactTable from "react-table-6";
import "react-table-6/react-table.css";
import { getInitialFlightData } from "./DataProvider";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: getInitialFlightData()
    };

    this.columns = [
      {
        Header: "Origin",
        accessor: "origin"
      },
      {
        Header: "Flight",
        accessor: "flight"
      },
      {
        Header: "Arrival",
        accessor: "arrival"
      },
      {
        Header: "State",
        accessor: "state"
      }
    ];
  }

  render() {
    return (
      <div className="App">
        <ReactTable data={this.state.data} columns={this.columns} />
      </div>
    );
  }
}

export default App;