'use strict';

import React from 'react';
import ReactDOM from 'react-dom';
import SearchForm from "./components/search-form";

class App extends React.Component {
    render() {
        return (
            <div className="App">
                <SearchForm/>
            </div>
        )
    }
}

ReactDOM.render(
    <App />,
    document.getElementById('react')
)