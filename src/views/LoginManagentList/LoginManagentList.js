import React, { useState, useEffect } from 'react';
import { makeStyles } from '@material-ui/styles';

import axios from 'utils/axios';
import { Page, Label } from 'components';
import { Header, Results } from './components';
import { colors } from '@material-ui/core';
import { Alert } from 'components';

const useStyles = makeStyles(theme => ({
  root: {
    padding: theme.spacing(3)
  },
  results: {
    marginTop: theme.spacing(3)
  }
}));

const LoginManagentList = () => {
  const classes = useStyles();

  const [logins, setLogins] = useState([]);

  const [page, setPage] = useState(1);
  const [size, setSize] = useState(20);
  const [order, setOrder] = useState('asc');
  const [orderBy, setOrderBy] = useState('firstName');

  const [numberOfElements, setNumberOfElements] = useState(0);
  const [totalPages, setTotalPages] = useState(0);
  const [isLoading, setLoading] = useState(false);
  const [isError, setIsError] = useState(false);
  const [error, setError] = useState({});

  useEffect(() => {
    let mounted = true;

    if (mounted) 
      fetchLogin(page, size, order, orderBy);

    return () => {
      mounted = false;
    };
  }, []);

  const fetchLogin = (page, size, order, orderBy) => {
    const sort = orderBy + ',' + order
    let params = {
      page, size, sort
    }

    setLoading(true);
    setIsError(false);
    setLogins([]);
    setOrder(order);
    setOrderBy(orderBy);

    axios.get('/account-service/v1/user', { params }).then(response => {
      handleRequestSuccess(response);
    }).catch((error) => {
      handleRequestError(error);
    }).finally(() => {
      handleRequestFinally();
    });
  };

  const handleRequestSuccess = (response) => {
    setLogins(response.data.content);
    setNumberOfElements(response.data.numberOfElements);
    setTotalPages(response.data.totalPages);
    setPage(response.data.number);
    setSize(response.data.size);
    setIsError(false);
  }

  const handleRequestError = (error) => {
    setIsError(true);
    setError(error.response.data);
  }

  const handleRequestFinally = () => {
    setLoading(false);
  }

  const handleRequestSort = async (event, property) => {
    const isAsc = orderBy === property && order === 'asc';
    fetchLogin(page, size, (isAsc ? 'desc' : 'asc'), property);
  };

  const handleRequestPagination = (event, page, size) => {
    fetchLogin(page, size, order, orderBy);
  };

  const handleFilter = () => {console.log('handleFilter')};
  const handleSearch = () => {};

  return (
    <Page
      className={classes.root}
      title="Pessoas"
    >
      { !isLoading && isError && 
        <Alert
          variant="error"
          className={classes.alert}
          message={error.error}
        />
      }
      <Header
        onFilter={handleFilter}
        onSearch={handleSearch}
        numberOfElements={numberOfElements}
      />
      {(!isError) && (<>
      <Results
        className={classes.results}
        logins={logins}
        numberOfElements={numberOfElements}
        totalPages={totalPages}
        fetchLogin={fetchLogin}
        isLoading={isLoading}
        onRequestSort={handleRequestSort}
        onRequestPagination={handleRequestPagination}
        order={order}
        orderBy={orderBy}
        page={page}
        size={size}
      />
      </>)}
    </Page>
  );
};

export default LoginManagentList;
