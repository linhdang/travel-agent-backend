import React from 'react';
import Form from 'react-bootstrap/Form';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Table from 'react-bootstrap/Table';


class SearchForm extends React.Component {
    constructor(props) {
      super(props);
      this.state = {
        departure: '',
        arrival: '',
        from: '',
        to: '',
        accommodations: [],
        flights: []
      }
  
      this.handleChange = this.handleChange.bind(this);
      this.handleSubmit = this.handleSubmit.bind(this);
    }
  
    handleChange(event) {
      this.setState({[event.target.name]: event.target.value});
    }
  
    handleSubmit(event) {
      event.preventDefault();
      console.log(this.state);
      const data = new FormData(event.target);
      fetch('/api/request-new-plan', {
        method: 'POST',
        body: data,
      }).then(response => response.json()).then(json => {
          console.log(json)
          let accommodations=[]
          for (let i=0 ; i < json['proposedAccomodations'].length; i++) {
             accommodations.push(json['proposedAccomodations'][i]);
          }
          let flights=[]
          for (let i=0 ; i < json['proposedFlights'].length; i++) {
              flights.push(json['proposedFlights'][i]);
          }
          this.setState({"flights": flights});
          this.setState({"accommodations": accommodations});
      });
    }
  
    render() {
      return (
        <div>
          <h2>Search for tours</h2>
          <Form onSubmit={this.handleSubmit}>
            <Form.Group as={Row} controlId="departure">
              <Form.Label column sm="2">
                Departure
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="departure" as="select" value={this.state.departure} onChange={this.handleChange}>
                    <option>Dublin</option>
                    <option>London</option>
                    <option>Paris</option>
                    <option>Ho Chi Minh City</option>
                </Form.Control>
              </Col>
            </Form.Group>

            <Form.Group as={Row} controlId="arrival">
              <Form.Label column sm="2">
                Arrival
              </Form.Label>
              <Col sm="10">
                  <Form.Control name="arrival" as="select" value={this.state.arrival} onChange={this.handleChange}>
                    <option>Dublin</option>
                    <option>London</option>
                    <option>Paris</option>
                    <option>Ho Chi Minh City</option>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="from">
              <Form.Label column sm="2">
                From
              </Form.Label>
              <Col sm="10">
                <Form.Control name="from" type="date" onChange={this.handleChange}>
                </Form.Control>
              </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="to">
              <Form.Label column sm="2">
                To
              </Form.Label>
              <Col sm="10">
                <Form.Control name="to" type="date" onChange={this.handleChange}>
                </Form.Control>
              </Col>
            </Form.Group>
            <Button variant="primary" type="submit">
              Submit
            </Button>
          </Form>
          <h2> Accommodations</h2>
          <Table striped bordered hover size="sm">
            <thead>
            <tr>
              <th>Accommodation Type</th>
              <th>Provider</th>
              <th>Price</th>
            </tr>
            </thead>
            <tbody>
            {
              this.state.accommodations.map((value, index) => {
              return <tr> <td> {value.accomodationType}</td><td> {value.provider}</td><td> {value.price}</td></tr>
              })
            }


            </tbody>
          </Table>
          <h2> Flights</h2>
          <Table striped bordered hover size="sm">
              <thead>
              <tr>
                <th>Provider</th>
                <th>Price</th>
                <th>Date</th>
              </tr>
              </thead>
              <tbody>
              {
                this.state.flights.map((value, index) => {
                  const date = new Date(value.date).toLocaleDateString();
                  return <tr> <td> {value.provider}</td><td> {value.price}</td><td> {date}</td></tr>
                })
              }
              </tbody>
          </Table>
        </div>
      );
    }
  }

  export default SearchForm;
