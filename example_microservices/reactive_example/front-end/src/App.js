import React, { Component } from "react";
import ReactTable from "react-table-6";
import "react-table-6/react-table.css";
import { getInitialPatientData } from "./DataProvider";

class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      data: getInitialPatientData()
    };

    this.columns = [
      {
        Header: "First Name",
        accessor: "firstName"
      },
      {
        Header: "Last Name",
        accessor: "lastName"
      }
    ];
  this.eventSource = new EventSource("http://localhost:8080/allPatients");
  }

  componentDidMount() {
    this.eventSource.onmessage = e =>
      this.updatePatientState(JSON.parse(e.data));
  }

  updatePatientState(patientState) {
    let newData = this.state.data.map(patient=> {
        patient.firstName = patientState.firstName ;
      return patient;
    });

    this.setState(Object.assign({}, { data: newData }));
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